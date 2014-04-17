package com.alvinalexander.wikireaderserver

import com.alvinalexander.applescript.AppleScriptUtils
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

object WikipediaReaderUtils {

  def endOfParagraphDelay { Thread.sleep(Resources.END_OF_PARAGRAPH_DELAY) }
  def endOfSentenceDelay { Thread.sleep(Resources.END_OF_SENTENCE_DELAY) }
  def semiColonPause {  Thread.sleep(Resources.SEMI_COLON_PAUSE) }

  def speakWithDelay(sentence: String, voice: String = Resources.ALEX_VOICE) {
      speak(sentence, voice)
      Thread.sleep(Resources.END_OF_SENTENCE_DELAY)
  }

  def speak(sentence: String, voice: String = Resources.ALEX_VOICE) {
      val thingToSay = "say \"%s\" using \"%s\"".format(sentence, voice)
      try {
          getScriptEngine.eval(thingToSay)
      } catch {
          case e: ScriptException => // TODO
      }
  }

  // TODO don't keep getting a new engine; just do this once on init
  def getScriptEngine: ScriptEngine = {
      val sem = new ScriptEngineManager
      val appleScriptEngine = sem.getEngineByName("AppleScript")
      if (appleScriptEngine == null) println("appleScriptEngine is null, about to fail")
      appleScriptEngine
  } 

}





