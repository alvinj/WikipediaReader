#!/bin/sh

OUTPUT_JAR=target/scala-2.10/WikiReaderServer-assembly-0.1.jar
MAIN_CLASS=com.alvinalexander.wikireaderserver.Server

# ------------
# SBT Assembly
# ------------

sbt "set offline := true" assembly

if [ $? != 0 ]
then
  echo "Compile/assemble failed, exiting"
  exit 1
fi

# ---
# RUN
# ---

java -classpath $OUTPUT_JAR -Dapple.awt.UIElement=true $MAIN_CLASS

