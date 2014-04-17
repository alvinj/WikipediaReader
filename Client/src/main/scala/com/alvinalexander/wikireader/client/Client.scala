package com.alvinalexander.wikireader.client

import com.apple.eawt._
import java.io.File
import java.nio.file.Files
import java.nio.file.FileSystems
import grizzled.slf4j.Logging

/**
 * This object kicks off the action.
 * Logging methods are debug, info, error, trace, warn.
 */
object Client extends App with Logging {
//object Client extends App {

    debug("***** STARTING Client APPLICATION *****")
    initForMac
    installServerJar
    new MainController
  
    def initForMac {
        val macApplication = Application.getApplication
        val macAdapter = new MyMacAdapter
        macApplication.addApplicationListener(macAdapter)
    }

    /**
     * if it's not already there, copy the Server jar file from src/main/resources 
     * to the Library/AppSupport folder.
     */
    def installServerJar {
        val serverFile = new File(Resources.CANON_SERVER_JAR_FILENAME)
        if (!serverFile.exists) {
            debug("Server jar file does not exist, creating it now")
            (new File(Resources.SERVER_JAR_DIR)).mkdirs
            val is = getClass.getResourceAsStream(Resources.SERVER_JAR_NAME)
            val path = FileSystems.getDefault.getPath(Resources.SERVER_JAR_DIR, Resources.SERVER_JAR_NAME)
            Files.copy(is, path)
            is.close
            Thread.sleep(250)
        }
    }

}

/**
 * Handle the OS X 'quit' event.
 */
class MyMacAdapter extends ApplicationAdapter {
  
    /**
     * TODO find a better way to kill the Server process.
     */
    override def handleQuit(e: ApplicationEvent) {
        import scala.sys.process._
        def getServerPid = Process(Seq("/bin/sh", "-c", "ps auxw | grep WikipediaReaderServer | grep -v grep")).!!.trim.split("\\s+")(1)    
        val serverPid = getServerPid
        val exitCode = Process(Seq("/bin/sh", "-c", s"kill $serverPid")).!
        System.exit(0)
    }

    override def handleAbout(e: ApplicationEvent) {}
    override def handlePreferences(e: ApplicationEvent) {}

}




