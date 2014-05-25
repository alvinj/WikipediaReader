package com.alvinalexander.wikipediareader

object TextUtils {

  /**
   *  Break a paragraph into a sequence of strings by splitting on the decimal/period.
   *  Usage note: When using this method, you don't need the other methods in this class.
   */
  def breakParagraphIntoSentences(paragraph: String): Seq[String] = {
      val cleanParagraph = cleanWikipediaSentence(paragraph)
      val sentences = cleanParagraph.split("\\.")
      for {
          sentence <- sentences
      } yield s"${sentence.trim}"
  }
  
  /**
   * Clean all the junk out of a Wikipedia sentence, such as parentheses, brackets, etc.
   * TODO fix this code. it "works" right now, but the pattern matching is a crazy kludge. `replaceAll` doesn't seem
   * to do what it claims in these cases, or more likely, I don't know how to write the patterns properly.
   */
  def cleanWikipediaSentence(sentence: String): String = {
      var a = removeNonAsciiChars(sentence)
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'

      a = a.replaceAll("(.*) \\(.+\\)(.*)", "$1 $2")  // remove trailing space. probably just needed for tests.
 
      a = a.replaceAll("(.*) *\\(.+\\)[ ,;\\.](.*)", "$1 $2")  // add possible characters after parens
      a = a.replaceAll("(.*) *\\(.+\\)[ ,\\.](.*)", "$1 $2")
      a = a.replaceAll("(.*) *\\(.+\\)[ ,\\.](.*)", "$1 $2")

      a = a.replaceAll("(.*)\\[.+\\][ ,;\\.](.*)", "$1 $2")  // remove '[...]'
      a = a.replaceAll("(.*)\\[.+\\][ ,;\\.](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\](.*)", "$1 $2")

      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll(" \\.", ".")
      a.trim
  }

  // TODO figure out how to do this properly (kludge)
  def removeNonAsciiChars(s: String) = {
      var a = s.replaceAll("[^\\x00-\\x7F]", "")
      a = a.replaceAll("[^\\x20-\\x7E]", "")
      a = a.replaceAll("\\P{InBasic_Latin}", "")
      a
  }

  
}




