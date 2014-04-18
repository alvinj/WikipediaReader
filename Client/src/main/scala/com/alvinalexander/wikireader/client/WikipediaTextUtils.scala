package com.alvinalexander.wikireader.client

/**
 * The purpose of this class is to handle, massage, and manipulate the
 * text from a Wikipedia web page. In short, the text needs to be cleaned
 * up and enhanced for reading, and this object contains methods to do
 * that cleanup work.
 */
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
      val sentences2 = for {
          sentence <- sentences
      } yield s"${sentence.trim}."
      makeBetterSentences(sentences2)
  }
  
  /**
   * Analyze the sentences twice, then clean up.
   */
  def makeBetterSentences(sentences: Seq[String]): Seq[String] = 
      removeMultipleBlankSpaces(trim(reAnalyzeSentences(reAnalyzeSentences(sentences))))
  
  /**
   * TODO improve this algorithm and clean it up
   * TODO this method will die a horrible death if the last sentence matches a pattern,
   *      because it will try to reach out for the 'next' sentence
   * The first attempt at splitting sentences is naive, so re-join sentences as needed.
   */
  private def reAnalyzeSentences(sentences: Seq[String]): Seq[String] = {
      var skip1 = false
      var currSentence = ""
      val newSentences = for ((s,c) <- sentences.zipWithIndex) yield {
          if (skip1) {
              skip1 = false
              ""
          } else if (c == sentences.length-1) {
              // last sentence; don't try pattern matching, because there's nothing else to join with
              s
          } else {
              currSentence = s
              if (s.matches(".* [a-zA-Z]{2}\\.$")) {    // "Ph." or "Lt." or "Mr."
                  currSentence = currSentence + " " + sentences(c+1)
                  skip1 = true
              } else if (s.matches(".* [a-zA-Z]{2}\\.[a-zA-Z]\\.$")) {    // "Ph.D."
                  currSentence = currSentence + " " + sentences(c+1)
                  skip1 = true
              }
              currSentence
          }
      }
      removeBlankStrings(newSentences)
  }
  
  def trim(strings: Seq[String]) = strings.map(_.trim)
  def removeMultipleBlankSpaces(strings: Seq[String]) = strings.map(removeMultipleBlanks(_))  
  def ltrim(s: String) = s.replaceAll("^\\s+", "")
  def rtrim(s: String) = s.replaceAll("\\s+$", "")
  def removeMultipleBlanks(s: String) = s.replaceAll("  "," ")
  
  private def removeBlankStrings(strings: Seq[String]) = strings.filterNot(_.trim.equals(""))
  
  /**
   * Clean all the junk out of a Wikipedia sentence, such as parentheses, brackets, etc.
   * TODO fix this code. it "works" right now, but the pattern matching is a crazy kludge. `replaceAll` doesn't seem
   * to do what it claims in these cases, or more likely, I don't know how to write the patterns properly.
   * Note: My problems are due to possessive vs greedy quantifiers;
   *       @see http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html
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

      a = a.replaceAll("(.*?[a-zA-Z0-9]) and (.*?)", "$1, and $2")  // change "foo and bar" to "foo, and bar"

      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll("  ", " ")              // remove double spaces resulting from previous code
      a = a.replaceAll(" \\.", ".")
      a = a.replaceAll("\"", "")               // remove all " (do something else in future?)
      a = a.replaceAll("&#160;", " ")          // non-breaking space
      a = a.replaceAll("&nbsp;", " ")          // non-breaking space
      a = a.replaceAll("&#169;", "copyright")
      a = a.replaceAll("&amp;", "&")

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




