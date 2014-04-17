package com.alvinalexander.wikireader.client

/**
 * I made some changes somewhere -- or perhaps because I upgraded Java and Scala --
 * the `lines_!` method starting acting differently. As a result, I'm currently
 * running starting it in a separate Thread here.
 */
class Server extends Thread {

    override def run {
        import scala.sys.process._
        val serverProcess = Process(Seq(
            "java", 
            "-classpath", 
            Resources.CANON_SERVER_JAR_FILENAME,
            "-Dapple.awt.UIElement=true",
            Resources.SERVER_MAIN_CLASS))
        val serverProcessStream = serverProcess.lines_!
    }
  
}