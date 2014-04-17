package com.alvinalexander.wikireaderserver

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.ActorRef
import com.alvinalexander.wikipediareader.common.SpeakSentence

// this works (headless on Mac)
// (1) compile/package with sbt-assembly
// (2) run like this:
// java -classpath target/scala-2.10/WikiReaderServer-assembly-0.1.jar -Dapple.awt.UIElement=true com.alvinalexander.wikireaderserver.Server
//
// see http://web.archiveorange.com/archive/v/Hd19lAcrkdsJEtZmYiiQ
//
object Server extends App  {

    val system = ActorSystem("WikiReaderServerSystem")
    val sentenceSpeaker = system.actorOf(Props[SentenceSpeaker], name = "SentenceSpeaker")
    sentenceSpeaker ! "The speaker is alive"

}

/**
 * All this class does is speak the sentence it is given,
 * with the voice it is given. After the sentence is spoken, it sends a message 
 * back to its parent telling the parent it is finished.
 */
class SentenceSpeaker extends Actor {
  var client: ActorRef = _
  def receive = {
      case SpeakSentence(sentence, voice) =>
          client = sender
          speakSentence(sentence, voice)
      case sentence: String =>
          client = sender
          speakSentence(sentence)
      case _ => 
          println("SentenceSpeaker got an unexpected message")
  }

  def speakSentence(sentence: String, voice: String) {
      WikipediaReaderUtils.speak(sentence, voice)
      // TODO move this delay to the Client
      WikipediaReaderUtils.endOfSentenceDelay
      client ! Resources.READY
  }

  def speakSentence(sentence: String) {
      WikipediaReaderUtils.speak(sentence, Resources.ALEX_VOICE)
      // TODO move this delay to the Client
      WikipediaReaderUtils.endOfSentenceDelay
      client ! Resources.READY
  }


}







