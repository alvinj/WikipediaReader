package com.alvinalexander.wikireader.client

import java.io.ByteArrayInputStream
import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter

class TextUtilsSpec extends FunSpec with BeforeAndAfter {

    val testPage = MartinOderskyPage.content
    
    // TODO when i set this to null and tried to populate it in `before` i'd get a NullPointerException
    var inputStream = new ByteArrayInputStream(testPage.getBytes)

    // this wiki page should result in these four paragraphs
    val p0s0 = "Martin Odersky is a German computer scientist, and professor of programming methods at EPFL in Switzerland."
    val p0s1 = "He specializes in code analysis, and programming languages."
    val p0s2 = "He designed the Scala programming language, and Generic Java, and built the current generation of javac, the Java compiler."
    val p0s3 = "In 2007 he was inducted as a Fellow of the Association for Computing Machinery."

    before {
      //println("\n\nRUNNING BEFORE\n\n")
      inputStream = new ByteArrayInputStream(testPage.getBytes)
    }

    after {
      //println("\n\nRUNNING AFTER\n\n")
    }
    
    describe("foo bar") {
        it("0 == 0") { assert(0 == 0) }
    }

    describe("testing TextUtils::cleanWikipediaSentence and TextUtils::breakParagraphIntoSentences") {
        if (inputStream == null) println("\n\nINPUT STREAM IS NULL\n\n")
        val rawParagraphs = WikipediaUtils.getRawParagraphs(inputStream)
        it("should have eight paragraphs") {
            assert(rawParagraphs.length == 8)
        }
        val p0 = rawParagraphs(0)
        val p0Cleaned = WikipediaTextUtils.cleanWikipediaSentence(p0)
        val sentences = WikipediaTextUtils.breakParagraphIntoSentences(p0Cleaned)
        println(s"===== ${sentences(0)} =====")
        it("p0Cleaned should have four sentences") { assert(sentences.length == 4) }
        it("p0s0 should be correct") { assert(sentences(0) == p0s0) }
        it("p0s1 should be correct") { assert(sentences(1) == p0s1) }
        it("p0s2 should be correct") { assert(sentences(2) == p0s2) }
        it("p0s3 should be correct") { assert(sentences(3) == p0s3) }
    }

    describe("testing TextUtils::returnCleanedParagraphs") {
        inputStream = new ByteArrayInputStream(testPage.getBytes)
        val rawParagraphs = WikipediaUtils.getRawParagraphs(inputStream)
        it("should have eight raw paragraphs") {
            assert(rawParagraphs.length == 8)
        }
        val cleanedParagraphs = WikipediaTextUtils.returnCleanedParagraphs(rawParagraphs)
        it("should have five clean paragraphs") {
            assert(cleanedParagraphs.length == 4)
        }
        it("fourth paragraph should not end with '[4]'") {
            assert(!cleanedParagraphs(3).endsWith("[4]"))
        }
    }
    
    // expected result:
    //"In 1989, he received his Ph.D. from ETH Zurich under Niklaus Wirth, who is best known as the designer of several programming languages (including Pascal).", 
    //" He did postdoctoral work at IBM and Yale."
//    describe("testing TextUtils::reAnalyzeSentences") {
//        import WikipediaTextUtils._
//        val rawSentences = MartinOPhDParagraph.sentences
//        // analyze the sentences twice, then clean them up
//        val betterSentences = makeBetterSentences(rawSentences)
//        println("=== DEBUG ===")
//        betterSentences.foreach(println)
//        it ("should have two sentences") {
//            assert(betterSentences.length == 2)
//        }
//        it ("first sentence should match") {
//            assert(betterSentences(0) == "In 1989, he received his Ph.D. from ETH Zurich under Niklaus Wirth, who is best known as the designer of several programming languages (including Pascal).")
//        }
//        it ("second sentence should match") {
//            assert(betterSentences(1) == "He did postdoctoral work at IBM and Yale.")
//        }
//    }
    
    describe("clean run-on sentence") {
        val sentence = PeterFalkRunOnSentence.sentence
        val betterSentence = "He was nominated for an Academy Award twice, and won the Emmy Award on five occasions, and the Golden Globe Award once."
        val result = WikipediaTextUtils.cleanWikipediaSentence(sentence)
        println("=== DEBUG ===")
        println(result)
        assert(result == betterSentence)
    }

    
    
    
}








