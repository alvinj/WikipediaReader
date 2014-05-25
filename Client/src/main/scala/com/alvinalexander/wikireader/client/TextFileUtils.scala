package com.alvinalexander.wikireader.client

import java.net._
import java.io._
import scala.collection.mutable.ArrayBuffer

object TextFileUtils {
  
    /**
     * `url` should be a valid 'file' url, like "file:///Users/al/Desktop/50-Shades-Quotes.txt"
     */
    def getParagraphsFromFileUrl(urlString: String) = {        
        val url = new URL(urlString)
        val in = new BufferedReader(new InputStreamReader(url.openStream))
        val buffer = new ArrayBuffer[String]()
        var inputLine = in.readLine
        while (inputLine != null) {
            if (!inputLine.trim.equals("")) {
                buffer += inputLine.trim
            }
            inputLine = in.readLine
        }
        in.close
        buffer.toArray
    }

}


