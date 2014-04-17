package com.alvinalexander.wikireader.client

import javax.swing.ImageIcon

object Resources {

    // filesystem
    val SLASH = System.getProperty("file.separator")
    val USER_HOME_DIR = System.getProperty("user.home")
    val DB_DIR = USER_HOME_DIR + SLASH + "Library/Application Support/alvinalexander.com/WikipediaReader"
    
    // server
    val SERVER_MAIN_CLASS="com.alvinalexander.wikireaderserver.Server"
    val SERVER_JAR_NAME = "WikipediaReaderServer.jar"
    val SERVER_JAR_DIR = USER_HOME_DIR + SLASH + "Library" + SLASH + "Application Support" + SLASH + "alvinalexander.com" + SLASH + "WikipediaReader"
    val CANON_SERVER_JAR_FILENAME = SERVER_JAR_DIR + SLASH + SERVER_JAR_NAME
    //val CANON_SERVER_JAR_FILENAME = "/Users/al/Projects/Scala/WikipediaReader/Server/target/scala-2.10/WikiReaderServer-assembly-0.1.jar"
    
    // images
    val vcrBeginningImageUrl = getResource("/beginning.png")
    val vcrBeginningImageUrlClick = getResource("/beginningClick.png")
    val vcrBeginningImageUrlHover = getResource("/beginningHover.png")
    val vcrBeginningImage = new ImageIcon(vcrBeginningImageUrl)
    val vcrBeginningClickImage = new ImageIcon(vcrBeginningImageUrlClick)
    val vcrBeginningHoverImage = new ImageIcon(vcrBeginningImageUrlHover)
  
    val vcrRewindImageUrl = getResource("/rewind.png")
    val vcrRewindImageUrlClick = getResource("/rewindClick.png")
    val vcrRewindImageUrlHover = getResource("/rewindHover.png")
    val vcrRewindImage = new ImageIcon(vcrRewindImageUrl)
    val vcrRewindClickImage = new ImageIcon(vcrRewindImageUrlClick)
    val vcrRewindHoverImage = new ImageIcon(vcrRewindImageUrlHover)

    val vcrPlayImageUrl = getResource("/play.png")
    val vcrPlayImageUrlClick = getResource("/playClick.png")
    val vcrPlayImageUrlHover = getResource("/playHover.png")
    val vcrPlayImage = new ImageIcon(vcrPlayImageUrl)
    val vcrPlayClickImage = new ImageIcon(vcrPlayImageUrlClick)
    val vcrPlayHoverImage = new ImageIcon(vcrPlayImageUrlHover)

    val vcrPauseImageUrl = getResource("/pause.png")
    val vcrPauseImageUrlClick = getResource("/pauseClick.png")
    val vcrPauseImageUrlHover = getResource("/pauseHover.png")
    val vcrPauseImage = new ImageIcon(vcrPauseImageUrl)
    val vcrPauseClickImage = new ImageIcon(vcrPauseImageUrlClick)
    val vcrPauseHoverImage = new ImageIcon(vcrPauseImageUrlHover)

    val vcrFastForwardImageUrl = getResource("/ff.png")
    val vcrFastForwardImageUrlClick = getResource("/ffClick.png")
    val vcrFastForwardImageUrlHover = getResource("/ffHover.png")
    val vcrFastForwardImage = new ImageIcon(vcrFastForwardImageUrl)
    val vcrFastForwardClickImage = new ImageIcon(vcrFastForwardImageUrlClick)
    val vcrFastForwardHoverImage = new ImageIcon(vcrFastForwardImageUrlHover)

    val vcrEndImageUrl = getResource("/end.png")
    val vcrEndImageUrlClick = getResource("/endClick.png")
    val vcrEndImageUrlHover = getResource("/endHover.png")
    val vcrEndImage = new ImageIcon(vcrEndImageUrl)
    val vcrEndClickImage = new ImageIcon(vcrEndImageUrlClick)
    val vcrEndHoverImage = new ImageIcon(vcrEndImageUrlHover)
  
    def getResource(s: String) = getClass.getResource(s)
  
}


