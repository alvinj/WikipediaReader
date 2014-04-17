package com.alvinalexander.wikireader.client

import com.alvinalexander.macios7.iOS7Frame
import com.alvinalexander.macios7.iOS7TabbedPane
import java.awt.Color
import java.awt.Dimension
import java.awt.dnd.DropTarget
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JOptionPane
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.event.WindowEvent

class MainFrameController(main: MainController) 
extends DropHandlerInterface
{

  val backgroundColor = new Color(248, 248, 248)
  val alphaComposite = 0.96f
  val title = "Wikipedia Reader"
  val frameSize = new Dimension(720, 510)  // height sized to see 6 urls
  val TAB_WIDTH = 320

  // initial config
  val headerPanel = new HeaderPanel
  val mainPanel = new WikiReaderMainPanel2
  
  // voices panel
  val voicesPanelController = new VoicesPanelController(main)
  val voicesPanel = voicesPanelController.voicesPanel
  //voicesPanelController.setSelectedVoice(preferredVoice)

  // vcr controls
  val beginningButton = mainPanel.getBeginningBtnLabel
  val rewindButton = mainPanel.getRewindBtnLabel
  val playPauseButton = mainPanel.getPlayPauseBtnLabel
  val fastForwardButton = mainPanel.getFastForwardBtnLabel
  val endButton = mainPanel.getEndBtnLabel

  // tabpanel
  val MAIN_PANEL_LABEL = "Main"
  val VOICES_PANEL_LABEL = "Voices"
  val tp = new iOS7TabbedPane
  tp.tabWidth = TAB_WIDTH
  tp.setVGap(16)
  tp.addComponent(mainPanel, MAIN_PANEL_LABEL)
  tp.addComponent(voicesPanel, VOICES_PANEL_LABEL)
  tp.setActiveTab(MAIN_PANEL_LABEL)

  // colors
  tp.setOpaque(false)
  tp.tabsPanel.setOpaque(false)
  tp.cardsPanel.setOpaque(false)  // TODO this one was needed to get all colors the same
  mainPanel.setOpaque(false)
  mainPanel.getButtonPanel.setOpaque(false)
  voicesPanel.setOpaque(false)

  // mainframe
  val mainFrame = new iOS7Frame(tp)
  mainFrame.setBackgroundColor(backgroundColor)
  mainFrame.setAlphaComposite(alphaComposite)
  mainFrame.getRootPane.setOpaque(false)
  mainFrame.setTitle(title)
  mainFrame.setTitleColor(Color.BLACK)
  mainFrame.setSize(frameSize)
  mainFrame.setMinimumSize(frameSize)
  mainFrame.setPreferredSize(frameSize)
  mainFrame.setResizable(true)
  
  // drop target
  val dropTarget = new DropTarget(mainPanel, new DropTargetImplementation(this, mainPanel))
  
  // button rollover effects
  configureVcrButtonRolloverEffects

  // load our data
  import scala.collection.JavaConversions._
  class UrlListModel extends javax.swing.DefaultListModel[String] {}
  val urlsModel = new UrlListModel
  UrlsDataStore.init
  val urls = UrlsDataStore.getUrls
  for (url <- urls) {
    urlsModel.addElement(url)
  }
  mainPanel.getUrlsList.setModel(urlsModel)
  val urlsList = mainPanel.getUrlsList

  // let user work with jlist from the keyboard
  urlsList.addKeyListener(new KeyAdapter {
    override def keyPressed(e: KeyEvent)
    {
      if (e.getKeyCode == KeyEvent.VK_DELETE)
      {
        val selectedItem = urlsList.getSelectedValue
        urlsModel.removeElement(selectedItem)
        UrlsDataStore.removeUrl(selectedItem)
      } else if (e.getKeyCode == KeyEvent.VK_ENTER) {
        handleUrlFieldAction
      }
    }
   })
   

  // TODO refactor; there's too much going on here.
  // TODO this should happen on a thread/future
  // TODO move this logic to the MainController
  def handleUrlFieldAction {
    val url = getSelectedUrl
    main.handleUrlFieldAction(url)
  }
  
  def getSelectedUrl = mainPanel.getUrlsList.getSelectedValue
  
  /**
   * Handle URL Drag 'n Drop
   * -----------------------
   */

  def handleDropEvent(url: String) {
    UrlsDataStore.addUrl(url)
    urlsModel.addElement(url)
  }
  
  /**
   * VCR buttons
   * 
   * TODO move this code to a MainFrameController
   * 
   */
  
  import Resources._
  
  // vcr buttons
  // TODO move all of this logic to the MainController?
  def configureVcrButtonRolloverEffects {
    beginningButton.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent) { beginningButton.setIcon(vcrBeginningHoverImage) }
      override def mouseExited(e: MouseEvent) { beginningButton.setIcon(vcrBeginningImage) }
      override def mouseClicked(e: MouseEvent) {
        // TODO naive implementation of this effect on all clicks
        beginningButton.setIcon(vcrBeginningClickImage)
        main.doFirstParagraphAction
      }
    })
    rewindButton.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent) { rewindButton.setIcon(vcrRewindHoverImage) }
      override def mouseExited(e: MouseEvent) { rewindButton.setIcon(vcrRewindImage) }
      override def mouseClicked(e: MouseEvent) { 
        rewindButton.setIcon(vcrRewindClickImage)
        main.doPreviousParagraphAction
      }
    })
    playPauseButton.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent) { 
        if (main.currentlySpeaking) {
          playPauseButton.setIcon(vcrPauseHoverImage)
        } else {
          playPauseButton.setIcon(vcrPlayHoverImage)
        }
      }
      override def mouseExited(e: MouseEvent) {
        if (main.currentlySpeaking) {
          playPauseButton.setIcon(vcrPauseImage)
        } else {
          playPauseButton.setIcon(vcrPlayImage)
        }
      }
      override def mouseClicked(e: MouseEvent) {
        if (!main.currentlySpeaking) { // not currently speaking
          if (main.speakingWasPaused && main.lastUrl == getSelectedUrl) {  // 'resume'
            playPauseButton.setIcon(vcrPlayClickImage)
            main.currentlySpeaking = true
            main.speakingWasPaused = false
            //playPauseButton.setIcon(vcrPauseImage)
            main.resumeSpeaking
          } else {
            // NOTE may need some other 'else if' conditions here
            playPauseButton.setIcon(vcrPlayClickImage)
            main.currentlySpeaking = true
            main.speakingWasPaused = false
            //playPauseButton.setIcon(vcrPauseImage)
            handleUrlFieldAction
          }
        } else {
          // was speaking, so here we pause it
          playPauseButton.setIcon(vcrPauseClickImage)
          main.currentlySpeaking = false
          main.speakingWasPaused = true
          playPauseButton.setIcon(vcrPlayImage)
          Thread.sleep(200)
          main.handleStopSpeakingAction
        }
        
      }
    })
    fastForwardButton.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent) { fastForwardButton.setIcon(vcrFastForwardHoverImage) }
      override def mouseExited(e: MouseEvent) { fastForwardButton.setIcon(vcrFastForwardImage) }
      override def mouseClicked(e: MouseEvent) { 
        fastForwardButton.setIcon(vcrFastForwardClickImage)
        main.doNextParagraphAction
      }
    })
    endButton.addMouseListener(new MouseAdapter {
      override def mouseEntered(e: MouseEvent) { endButton.setIcon(vcrEndHoverImage) }
      override def mouseExited(e: MouseEvent) { endButton.setIcon(vcrEndImage) }
      override def mouseClicked(e: MouseEvent) { 
        endButton.setIcon(vcrEndClickImage)
        main.doLastParagraphAction
      }
    })
  }
  
}










