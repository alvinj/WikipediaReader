#!/bin/sh

OUTPUT_JAR=target/scala-2.10/WikiReaderServer-assembly-0.1.jar
MAIN_CLASS=com.alvinalexander.wikireaderserver.Server

java -classpath $OUTPUT_JAR -Dapple.awt.UIElement=true $MAIN_CLASS

