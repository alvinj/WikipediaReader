package com.alvinalexander.wikireader.client

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import javax.swing.SwingUtilities
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import com.alvinalexander.wikipediareader.common._
import akka.actor.ActorRef

/**
 * The main Speaker class that other classes should address.
 * It has the responsibility to speak the Wikipedia story paragraphs
 * in the given voice.
 */
class Speaker(uiHelper: ActorRef, mainController: MainController) extends Actor {

  val speakerHelper = context.actorOf(Props(new SpeakerHelper(uiHelper)), name = "SpeakerHelper")

  def receive = {
      case Speak(paragraphs, voice) => speakerHelper ! Speak(paragraphs, voice)
      case SpeakWithMultipleVoices(paragraphs, voices) => speakerHelper ! SpeakWithMultipleVoices(paragraphs, voices)
      case SetVoice(voice) => speakerHelper ! SetVoice(voice)
      case SetVoices(voices) => speakerHelper ! SetVoices(voices)
      case StopSpeaking => speakerHelper ! StopSpeaking      
      case EndOfStoryReached =>
          // TODO this was a last-minute hack
          mainController.handleEndOfStoryReached
      case PauseSpeaking => speakerHelper ! PauseSpeaking
      case ResumeSpeaking => speakerHelper ! ResumeSpeaking
      case FirstParagraph => speakerHelper ! FirstParagraph 
      case PreviousParagraph => speakerHelper ! PreviousParagraph 
      case NextParagraph => speakerHelper ! NextParagraph 
      case LastParagraph => speakerHelper ! LastParagraph 
      case _ => println("Speaker got an unexpected message")
  }
}


/**
 * This class is told to speak a story, and knows how to speak it.
 * It can also be told to stop/pause speaking.
 * It can also be told to rewind or fast-forward.
 * 
 * TODO refactor all of this. my initial assumptions changed *a lot*.
 * - move the speaking delays (sentence, paragraph) here; sentence delay is currently in the server
 * - get Story chunk from Speaker
 * - convert story into paragraphs
 * - feed one sentence at a time to the sentence speaker
 * - keep track of the last paragraph and sentence spoken
 * - SentenceSpeaker will tell you when it's ready for the next sentence
 * - check to see if you have been stopped or paused before sending each sentence
 * - don't use null values
 */
class SpeakerHelper(uiHelper: ActorRef) extends Actor {

  val DEFAULT_VOICE = "Alex"
  val READY = "READY"

  val sentenceSpeaker = context.actorFor("akka://WikiReaderServerSystem@127.0.0.1:5150/user/SentenceSpeaker")
  
  var cleanParagraphs: Seq[String] = _
  var currentParagraphCounter = 0
  var currentSentenceCounter = 0
  var currentVoice = DEFAULT_VOICE
  var stopTalking = false
  var endOfStoryReached = false
  
  // multiple voice support
  var useMultipleVoices = false
  var currentVoiceCounter = 0
  var currentVoices: Seq[String] = _

  def receive = {
    case Speak(rawParagraphs, voice) => {
        currentVoice = voice
        useMultipleVoices = false
        cleanParagraphs = cleanRawWikipediaParagraphs(rawParagraphs)
        stopTalking = false  // needed for 'switch url' case
        startSpeakingStory
    }
    case SpeakWithMultipleVoices(rawParagraphs, voices) => {
        currentVoices = voices
        useMultipleVoices = true
        cleanParagraphs = cleanRawWikipediaParagraphs(rawParagraphs)
        stopTalking = false
        startSpeakingStory
    }
    case READY =>  speakNextSentence  // TODO this is currently the way the Server tells us it's ready for the next sentence
    case StopSpeaking =>
        stopTalking = true
    case PauseSpeaking => 
        stopTalking = true
    case ResumeSpeaking =>
        stopTalking = false
        speakNextSentence
    case SetVoice(voice) => {
        currentVoice = voice
        useMultipleVoices = false
    }
    case SetVoices(voices) => {
        currentVoices = voices
        useMultipleVoices = true
    }
    case FirstParagraph => doFirstParagraphAction
    case PreviousParagraph => doPreviousParagraphAction 
    case NextParagraph => doNextParagraphAction 
    case LastParagraph => doLastParagraphAction 
    case _ => println("SpeakerHelper got an unexpected message")
  }
  
  // some kludgy code here because scala doesn't have a ++ operator
  def getNextVoice = {
      if (currentVoiceCounter >= currentVoices.length-1) {
          val voiceNum = currentVoiceCounter
          currentVoiceCounter = 0
          currentVoices(voiceNum)
      } else {
          val voiceNum = currentVoiceCounter  // 0
          currentVoiceCounter += 1
          currentVoices(voiceNum)
      }
  }
  
  def cleanRawWikipediaParagraphs(wikiParagraphs: Seq[String]) = WikipediaTextUtils.returnCleanedParagraphs(wikiParagraphs) 

  def doFirstParagraphAction {
      initializeCounters
  }

  def doPreviousParagraphAction {
      if (currentParagraphCounter != 0) currentParagraphCounter -= 1
      currentSentenceCounter = 0
  }

  def doNextParagraphAction {
      if (currentParagraphCounter < cleanParagraphs.length-1) currentParagraphCounter += 1
      currentSentenceCounter = 0
  }

  def doLastParagraphAction {
      currentParagraphCounter = cleanParagraphs.length - 1
      currentSentenceCounter = 0
  }

  def startSpeakingStory {
      endOfStoryReached = false
      initializeCounters
      speakNextSentence
  }

  def initializeCounters {
      currentParagraphCounter = 0 
      currentSentenceCounter = 0
      currentVoiceCounter = 0
  }

  /**
   * Send one sentence at a time to the SentenceSpeaker.
   * Wait for the SentenceSpeaker to tell us it's ready for the next sentence.
   * Also let the user stop the speaking through the UI.
   * 
   * `paragraphs` can be considered to be cleaned by the time this method is run.
   */
  def speakNextSentence {
    
    if (stopTalking) return
    if (endOfStoryReached) return
    
    val currentParagraph = cleanParagraphs(currentParagraphCounter)
    var sentencesInCurrentParagraph = getSentencesFromParagraph(currentParagraph)
    val numSentences = sentencesInCurrentParagraph.length

    // if you've reached the last sentence of the last paragraph, tell our
    // parent the end of the story has been reached
    if (currentParagraphCounter == cleanParagraphs.length-1 && currentSentenceCounter == numSentences) {
        handleEndOfStoryReached
        return
    }

    // otherwise, if you've reached the last sentence of any other paragraph,
    // move on to the next paragraph
    if (currentSentenceCounter == numSentences) {
        incrementParagraphCounter
        pointSentenceCounterToFirstSentence
        sentencesInCurrentParagraph = getSentencesFromParagraph(getCurrentParagraph)
        // TODO this 'sleep' line is a hack, put here b/c the server doesn't know when we've reached the end of a paragraph
        // (but we do)
        Thread.sleep(400)
    }
    
    // `sentences` should be valid for the old paragraph or the next paragraph
    val currentSentence = sentencesInCurrentParagraph(currentSentenceCounter)

    // update the ui
    //context.actorSelection("/user/UIHelper") ! currentSentence
    uiHelper ! currentSentence
    
    if (useMultipleVoices && currentSentenceCounter==0) {
    //if (useMultipleVoices) {
        currentVoice = getNextVoice
    }

    // send it to the speaker
    sentenceSpeaker ! SpeakSentence(currentSentence, currentVoice)
    
    // prepare to move on to the next sentence.
    // TODO wait until SentenceSpeaker says it's done
    currentSentenceCounter += 1
  }
  
  def handleEndOfStoryReached {
      endOfStoryReached = true
      initializeCounters
      Thread.sleep(1000)  // TODO make this configurable
      sentenceSpeaker ! "End of story"
    
      // TODO this needs to wait until i get the message back from the 
      // SentenceSpeaker that it is finished speaking the last sentence.
      context.parent ! EndOfStoryReached
  }

  def incrementParagraphCounter {
      currentParagraphCounter += 1
  }
  def pointSentenceCounterToFirstSentence {
      currentSentenceCounter = 0
  }
  def getCurrentParagraph = WikipediaTextUtils.cleanWikipediaSentence(cleanParagraphs(currentParagraphCounter))
  def getSentencesFromParagraph(currentParagraph: String) = WikipediaTextUtils.breakParagraphIntoSentences(currentParagraph)

}






