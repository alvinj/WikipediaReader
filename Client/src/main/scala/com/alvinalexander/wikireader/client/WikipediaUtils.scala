package com.alvinalexander.wikireader.client

import org.htmlcleaner.HtmlCleaner
import scala.collection.mutable.ListBuffer
import java.net.URL
import org.apache.commons.lang3.StringEscapeUtils
import org.htmlcleaner.TagNode
import scala.collection.JavaConversions._
import java.net.HttpURLConnection
import java.io.InputStream
import scala.collection.mutable.ArrayBuffer

/**
 * Connection Info: http://stackoverflow.com/questions/18168337/html-cleaner-xpath-not-working-in-android-app
 * 
 * Resource: http://thinkandroid.wordpress.com/2010/01/05/using-xpath-and-html-cleaner-to-parse-html-xml/
 * Params: http://htmlcleaner.sourceforge.net/parameters.php
 * 
 */
object WikipediaUtils {

  /**
   * A `paragraph` is what it says it is; a paragraph from the given
   * Wikipedia page, represented by the InputStream. So a paragraph is
   * a sequence of one or more sentences. It might be like, "Al was born
   * in Illinois. He moved to Kentucky. He later moved to Alaska. He got
   * a degree in aerospace engineering along the way." This method does
   * all the initial cleanup work to convert extraneous Wikipedia stuff
   * into "clean" paragraphs and sentences.
   * 
   * TODO get the url content with a connection timeout
   * TODO clean up the junk in each sentence (parens, brackets, etc.)
   * TODO find a way to skip source code and other crap
   */
  def getRawParagraphs(is: InputStream): Array[String] = {
      val encoding = "iso-8859-1"
      val cleaner = new HtmlCleaner
      val props = cleaner.getProperties
      val rootTagNode = cleaner.clean(is, encoding)
      val elements = rootTagNode.getAllElements(false)

      // elements(0) is 'head', elements(1) is 'body'
      val body = elements(1)

      // the desired content is here
      val bodyId = "//div[@id='content']/div[@id='bodyContent']/div[@id='mw-content-text']"
      val objectArray = body.evaluateXPath(bodyId)
      val buffer = new ArrayBuffer[String]()
      if (objectArray.length > 0) {
          val topTagNode = objectArray(0).asInstanceOf[TagNode]
          val paragraphs = getRawParagraphs(topTagNode)
          for (p <- paragraphs) {
              buffer += p
          }
      }
      buffer.toArray
  }
  
  /**
   * Should the InputStream be buffered?
   * http://alvinalexander.com/blog/post/java/how-open-url-read-contents-httpurl-connection-java
   * 
   * Can throw java.net.UnknownHostException
   * 
   */
  def getUrlInputStream(url: String, 
                        connectTimeout: Int = 5000, 
                        readTimeout: Int = 5000, 
                        requestMethod: String = "GET") = {
      val u = new URL(url)
      val conn = u.openConnection.asInstanceOf[HttpURLConnection]
      HttpURLConnection.setFollowRedirects(false)
      conn.setConnectTimeout(connectTimeout)
      conn.setReadTimeout(readTimeout)
      conn.setRequestMethod(requestMethod)
      conn.connect
      conn.getInputStream
  }

  def getRawParagraphs(tagNode: TagNode) = {
      val listOfTagNodes = tagNode.getChildTagList.asInstanceOf[java.util.List[TagNode]]
      for {
          tn <- listOfTagNodes
          if tn.getAttributes.size == 0
      } yield tn.getText.toString
  }

  
}






