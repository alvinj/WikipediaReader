package com.alvinalexander.wikireader.client

import org.scalatest.{BeforeAndAfter, FunSpec}
import java.io.ByteArrayInputStream

class WikipediaUtilsTests extends FunSpec with BeforeAndAfter {

  val testPage = MartinOderskyPage.content
  val testPageInputStream = new ByteArrayInputStream(testPage.getBytes)
  
  describe("testing getRawParagraphs") {
      val rawParagraphs = WikipediaUtils.getRawParagraphs(testPageInputStream)
      //println("#paragraphs = " + rawParagraphs.length)
      //for ((p,i) <- rawParagraphs.zipWithIndex) println(s"$i $p")
      it("should have eight paragraphs") {
          assert(rawParagraphs.length == 8)
      }
  }

}

