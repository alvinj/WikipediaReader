#!/bin/sh

#
# remove old Server jar file
#
#rm ~/Library/Application\ Support/alvinalexander.com/WikipediaReader/WikipediaReaderServer.jar

# ------------
# SBT Assembly
# ------------

sbt assembly

if [ $? != 0 ]
then
  echo "Compile/assemble failed, exiting"
  exit 1
fi

# ---
# ANT
# ---

cd deploy
ant

if [ $? != 0 ]
then
  echo "Ant failed, exiting"
  exit 2
fi

sleep 1

# ---
# RUN
# ---

open release/WikiReader.app


