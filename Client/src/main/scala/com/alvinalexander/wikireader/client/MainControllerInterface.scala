package com.alvinalexander.wikireader.client

trait MainControllerInterface {
  // TODO it's a kludge to have these two methods like this.
  // one is currently needed in Actions, the other is needed in MainFrameController.
  def handleUrlFieldAction
  def handleUrlFieldAction(url: String)
  def showCurrentSentenceInMainFrame(sentence: String)
}

