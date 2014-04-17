package com.alvinalexander.wikireader.client

/**
 * The controller for the "Voices" panel in the UI.
 */
class VoicesPanelController(mainController: MainControllerInterface) {
  
  val voicesPanel = new WikiReaderVoicesPanel2
  val selectedVoiceTextField = voicesPanel.getVoiceTextField
  val desiredVoicesTextArea = voicesPanel.getVoicesTextArea
  val useOneVoiceRadioButton = voicesPanel.getUseVoiceRadioButton
  val alternateVoicesRadioButton = voicesPanel.getAlternateVoicesRadioButton
  val radioButtonGroup = voicesPanel.getRadioButtonGroup

  def getSelectedVoice = {
      val voice = selectedVoiceTextField.getText
      if (voice == null || voice.trim == "") "Alex" else voice
  }

  def setSelectedVoice(preferredVoice: String) {
      selectedVoiceTextField.setText(preferredVoice)
  }

  /**
   * Return the voices in the textarea as a Seq[String].
   */
  def getSelectedVoices: Seq[String] = {
      val rawVoicesText = desiredVoicesTextArea.getText  // "Alex\nVicki\nFred"
      val rawVoicesArray = rawVoicesText.trim.split("\n")
      val cleanedVoicesArray = for {
          v1 <- rawVoicesArray
          v2 = v1.trim
          if v2 != ""
      } yield v2
      cleanedVoicesArray.toSeq
  }

  /**
   * Set the list of voices.
   */
  def setVoices(voices: Seq[String]) {
      val multilineString = voices.mkString("\n")
      desiredVoicesTextArea.setText(multilineString)
  }

}







