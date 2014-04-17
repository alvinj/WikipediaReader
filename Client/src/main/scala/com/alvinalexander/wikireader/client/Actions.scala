package com.alvinalexander.wikireader.client

import java.awt.event.ActionListener
import java.awt.event.ActionEvent

/**
 * Handle the action in the UrlTextField, basically the user hitting [Enter]
 * in the textfield.
 */
class TextFieldActionListener(mainController: MainControllerInterface) extends ActionListener {

    def actionPerformed(e: ActionEvent) { 
        mainController.handleUrlFieldAction
    }

}
