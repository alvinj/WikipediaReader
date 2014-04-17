package com.alvinalexander.wikireader.client

import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import java.io.{File, BufferedWriter, FileWriter}

object VoicesDataStore extends BaseDataStore {

  val DB_FILE = DB_DIR + FILE_PATH_SEPARATOR + "voices.db"

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

  def getVoices: List[String] = {
      if ((new File(DB_FILE).exists)) {
          val voicesFromFile = for (line <- Source.fromFile(DB_FILE).getLines) yield line
          records.appendAll(voicesFromFile)
          records.toList
      } else {
          Nil
      }
  }
  
  def saveVoices(newVoices: Seq[String]) {
      records.clear
      records.appendAll(newVoices)
      saveVoicesToFile
  }

  private def saveVoicesToFile {
      val file = new File(DB_FILE) 
      val bw = new BufferedWriter(new FileWriter(file))
      for (voice <- records) bw.write(s"$voice\n")
      bw.close
  }

}



