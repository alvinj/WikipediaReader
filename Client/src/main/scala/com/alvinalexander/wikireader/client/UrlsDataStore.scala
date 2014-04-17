package com.alvinalexander.wikireader.client

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.io.{File, BufferedWriter, FileWriter}

object UrlsDataStore extends BaseDataStore {

  val DB_FILE = DB_DIR + FILE_PATH_SEPARATOR + "urls.db"

  /**
   * Creates the database directory if it does not already exist.
   */
  def init {
      val dir = new File(DB_DIR)
      if (!dir.exists) {
          val successful = dir.mkdirs
          if (!successful) {
              // TODO use a logger instead of this
          }
      }
  }
  
  def getUrls(): List[String] = {
      if ((new File(DB_FILE).exists())) {
          val urlsFromFile = for (line <- Source.fromFile(DB_FILE).getLines()) yield line
          records.appendAll(urlsFromFile)
          records.toList
      } else {
          Nil
      }
  }

  def addUrl(symbol: String) {
      records += symbol
      saveUrls
  }

  def removeUrl(symbol: String) {
      records -= symbol
      saveUrls
  }

  // save the urls (in sorted order)
  private def saveUrls {
      val file = new File(DB_FILE) 
      val bw = new BufferedWriter(new FileWriter(file))
      for (url <- records.sorted) bw.write(s"$url\n")
      bw.close
  }

}



