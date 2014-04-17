package com.alvinalexander.wikireader.client

object WikipediaTextUtils {

  /**
   * Take a series of "raw" paragraphs as input, clean them, and return the cleaned
   * results as a new set of paragraphs. Note that a paragraph is essentially just 
   * a String.
   * 
   * TODO may want to stop when the algorithm hits "References[edit]".
   */
  def returnCleanedParagraphs(rawParagraphs: Seq[String]): Seq[String] = {
      for {
          p1 <- rawParagraphs
          if ! (p1 == null)
          if ! p1.startsWith("References[edit]")
          if ! p1.startsWith("External links[edit]")
          if ! p1.startsWith("\nhome page")
          val p2 = cleanWikipediaSentence(p1)
          if ! p2.trim.equals("")
      } yield p2
  }
  
  /**
   *  Break a paragraph into a sequence of strings by splitting on the decimal/period.
   *  Usage note: When using this method, you don't need the other methods in this class.
   */
  def breakParagraphIntoSentences(paragraph: String): Seq[String] = {
      val cleanParagraph = cleanWikipediaSentence(paragraph)
      val sentences = cleanParagraph.split("\\.")
      for {
          sentence <- sentences
      } yield s"${sentence.trim}."
  }
  
  /**
   * Clean all the junk out of a Wikipedia sentence, such as parentheses, brackets, etc.
   * TODO fix this code. it "works" right now, but the pattern matching is a crazy kludge. `replaceAll` doesn't seem
   * to do what it claims in these cases, or more likely, I don't know how to write the patterns properly.
   * TODO rename this method; it works on any String, such as a paragraph.
   */
  def cleanWikipediaSentence(sentence: String): String = {
      var a = removeNonAsciiChars(sentence)
      a = a.replaceAll("i\\.e\\.,", "that is,")
      a = a.replaceAll(" i\\.e\\. ", " that is, ")
      a = a.replaceAll("i\\.e\\.", " , that is, ")     // poorly formatted stuff like "--i.e."
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'
      a = a.replaceAll("(.*) \\(.+\\) (.*)", "$1 $2")  // remove '(...)'

      a = a.replaceAll("(.*) \\(.+\\)(.*)", "$1 $2")  // remove trailing space. probably just needed for tests.

      a = a.replaceAll("(.*) *\\(.+\\)[ ,;\\.](.*)", "$1 $2")  // add possible characters after parens
      a = a.replaceAll("(.*) *\\(.+\\)[ ,\\.](.*)", "$1 $2")
      a = a.replaceAll("(.*) *\\(.+\\)[ ,\\.](.*)", "$1 $2")

      a = a.replaceAll("(.*)\\[.+\\][ ,;\\.](.*)", "$1 $2")  // remove '[...]'
      a = a.replaceAll("(.*)\\[.+\\][ ,;\\.](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\][ ,;\\.](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\](.*)", "$1 $2")
      a = a.replaceAll("(.*)\\[.+\\]", "$1")                 // remove '[...]' at the end of a string

      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll(" \\.", ".")
      a = a.replaceAll("\"", "")               // remove all " (do something else in future?)
      a = a.replaceAll("&#160;", " ")          // non-breaking space
      a = a.replaceAll("&nbsp;", " ")          // non-breaking space
      a = a.replaceAll("&#169;", "copyright")
      a.trim
  }

  // TODO figure out how to do this properly (kludge)
  private def removeNonAsciiChars(s: String) = {
      var a = s.replaceAll("[^\\x00-\\x7F]", "")
      a = a.replaceAll("[^\\x20-\\x7E]", "")
      a = a.replaceAll("\\P{InBasic_Latin}", "")
      a
  }

  
}




