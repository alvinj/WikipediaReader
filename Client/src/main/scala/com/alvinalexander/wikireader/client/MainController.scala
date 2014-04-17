package com.alvinalexander.wikireader.client

import akka.actor.ActorSystem
import javax.swing.SwingUtilities
import javax.swing.UIManager
import akka.actor.Props
import java.util.prefs.Preferences
import com.alvinalexander.wikipediareader.common._
import javax.swing.JOptionPane
import grizzled.slf4j.Logging

/**
 * This is the 'main' controller for the application.
 */
class MainController extends MainControllerInterface with Logging {

  // preferences
  val TRUE = "true"
  val FALSE = "false"
  val PREFS_PREFERRED_VOICE = "PREFERRED_VOICE"
  val PREFS_USE_ONE_VOICE = "USE_ONE_VOICE"
  val prefs = Preferences.userNodeForPackage(this.getClass)
  var preferredVoice = prefs.get(PREFS_PREFERRED_VOICE, "Alex")
  var lastUrl = ""

  // start the Server
  (new Server).start

  // state
  var currentlySpeaking = false
  var speakingWasPaused = false   // needed for pause/restart

  // controllers and references
  val mainFrameController = new MainFrameController(this)
  val voicesPanelController = mainFrameController.voicesPanelController
  val mainFrame = mainFrameController.mainFrame
  val mainPanel = mainFrameController.mainPanel

  // load voices
  initializeVoiceSettings
  
  // create the actor system
  val actorSystem = ActorSystem("WikipediaReaderSystem")

  // create the actors
  // TODO the only reason i'm passing the uiHelper to the Speaker is that i couldn't
  // get the SpeakerHelper to look up the uiHelper
  val uiHelper = actorSystem.actorOf(Props(new UIHelper(this)), name = "UIHelper")  
  val speaker = actorSystem.actorOf(Props(new Speaker(uiHelper, this)), name = "Speaker")  
  
  displayMainFrame

  // TODO kludge
  def handleUrlFieldAction {
      handleUrlFieldAction(mainFrameController.getSelectedUrl)
  }

  // TODO kludge
  def handleUrlFieldAction(url: String) {
    if (url != null) {
      lastUrl = url
      try {
        val is = WikipediaUtils.getUrlInputStream(url, 2000, 2000)
        val rawParagraphs = WikipediaUtils.getRawParagraphs(is)
        is.close
        speakParagraphs(rawParagraphs)
      } catch {
        case e: java.net.UnknownHostException =>
          JOptionPane.showMessageDialog(mainFrame, "Sorry, can't connect to the Internet.")
      }
    }
  }

  // TODO add this to the swing shutdown event handler
  //actorSystem.shutdown

  /**
   * called by the UIHelper to do what it says
   */
  def showCurrentSentenceInMainFrame(sentence: String) {
      SwingUtilities.invokeLater(new Runnable {
          def run {
              mainPanel.getWikiOutputArea.setText(sentence)
          }
      })
  }

  private def displayMainFrame {
      try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName())
      } catch {
          case e: Exception => println("WikiReader: Exception happened in MainController::displayMainFrame")
      }
      SwingUtilities.invokeLater(new Runnable() {
          def run {
              mainFrame.pack
              mainFrame.setLocationRelativeTo(null)
              mainFrame.setVisible(true)
              mainFrame.transferFocus
          }
      })
  }

  /**
   * TODO this method will screw up the oneVoice vs multipleVoices settings.
   */
  def resumeSpeaking {
      if (voicesPanelController.useOneVoiceRadioButton.isSelected) {
          val voice = voicesPanelController.getSelectedVoice
          rememberSingleVoiceSettings(voice)
          speaker ! SetVoice(voice)
      } else {
          val voices = voicesPanelController.getSelectedVoices
          rememberMultiVoiceSettings(voices)
          speaker ! SetVoices(voices)
      }
      speaker ! ResumeSpeaking
  }

  private def speakParagraphs(rawParagraphs: Array[String]) {
      if (voicesPanelController.useOneVoiceRadioButton.isSelected) {
          // speak with one voice
          val voice = voicesPanelController.getSelectedVoice
          rememberSingleVoiceSettings(voice)
          speaker ! Speak(rawParagraphs, voice)
      } else {
          // speak with multiple voices
          val voices = voicesPanelController.getSelectedVoices
          rememberMultiVoiceSettings(voices)
          speaker ! SpeakWithMultipleVoices(rawParagraphs, voices)
      }
  }
  
  def handleStopSpeakingAction {
      speaker ! StopSpeaking
  }
  
  private def rememberSingleVoiceSettings(voice: String) {
      rememberVoiceInUserPreferences(voice)
      rememberSingleMultiSetting(useSingleVoice = TRUE)
  }
  
  private def rememberMultiVoiceSettings(voices: Seq[String]) {
      rememberSingleMultiSetting(useSingleVoice = FALSE)
      VoicesDataStore.saveVoices(voices)
  }
  
  private def initializeVoiceSettings {
      VoicesDataStore.init
      val voices = VoicesDataStore.getVoices
      voicesPanelController.setSelectedVoice(preferredVoice)
      voicesPanelController.setVoices(voices.toSeq)
      initSingleMultiVoiceRadioButton
  }

  private def initSingleMultiVoiceRadioButton {
      val useSingleVoice = prefs.get(PREFS_USE_ONE_VOICE, TRUE)
      if (useSingleVoice == TRUE) {
          voicesPanelController.useOneVoiceRadioButton.setSelected(true)
          voicesPanelController.alternateVoicesRadioButton.setSelected(false)
      } else {
          voicesPanelController.useOneVoiceRadioButton.setSelected(false)
          voicesPanelController.alternateVoicesRadioButton.setSelected(true)
      }
  }
   
  private def rememberVoiceInUserPreferences(voice: String) {
      prefs.put(PREFS_PREFERRED_VOICE, voice)
  }
  
  private def rememberSingleMultiSetting(useSingleVoice: String) {
      prefs.put(PREFS_USE_ONE_VOICE, useSingleVoice)
    
  }

  def doFirstParagraphAction { speaker ! FirstParagraph }         // |<<
  def doPreviousParagraphAction { speaker ! PreviousParagraph }   // <<
  def doNextParagraphAction { speaker ! NextParagraph }           // >>
  def doLastParagraphAction { speaker ! LastParagraph }           // >>|

  def handleEndOfStoryReached {
      mainFrameController.setPlayPauseButtonToPlay
      currentlySpeaking = false
  }

  def getSelectedVoice = {
    // ???
  }


}







