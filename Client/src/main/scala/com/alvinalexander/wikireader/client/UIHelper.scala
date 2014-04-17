package com.alvinalexander.wikireader.client

import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

/**
 * This class takes messages from the SpeakerHelper and relays the 
 * necessary information to the user interface. The purpose of the class
 * is to help display the current sentence in the MainFrame of the user interface.
 */
class UIHelper(mainController: MainControllerInterface) extends Actor {

    def receive = {
        case s: String => mainController.showCurrentSentenceInMainFrame(s)
        case _ => // TODO log this
    }

}


