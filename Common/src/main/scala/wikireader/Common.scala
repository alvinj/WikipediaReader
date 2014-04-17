package com.alvinalexander.wikipediareader.common

case class Speak(paragrahs: Seq[String], voice: String = "Alex")
case class SpeakWithMultipleVoices(paragrahs: Seq[String], voices: Seq[String])

case class SpeakSentence(sentence: String, voice: String = "Alex")
case class RestartSpeaking(paragrahs: Seq[String], voice: String = "Alex")

case class SetVoice(voice: String)
case class SetVoices(voices: Seq[String])

case object StopSpeaking
case object PauseSpeaking
case object ResumeSpeaking
case object SentenceSpeakerReadyForNextSentence
case object EndOfStoryReached

case object FirstParagraph
case object PreviousParagraph
case object NextParagraph
case object LastParagraph

