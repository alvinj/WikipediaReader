#!/bin/sh

# use sbt-assembly to create the Server jar file.
# see the output for the location of the resulting jar file.
# if this doesn't appear to run, run `sbt clean` first.

sbt assembly


