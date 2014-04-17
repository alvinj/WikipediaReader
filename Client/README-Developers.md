Wikipedia Reader: Client (for developers)
=========================================

This is the developer documentation for the Client portion
of my Wikipedia reader application.

Background
----------

Some important notes about the Wikipedia Reader:

* It only runs on Mac OS X.
* It's written in Scala.
* I used a tool to build some of the UI with Java.
* It uses Akka Remote Actors to communicate between
  Client and Server portions of the application.
* As noted elsewhere, the Server was broken out from
  the Client because invoking the AppleScript `say`
  command makes the JVM hang/pause until the command
  is finished. (I don't yet know how to get around 
  that.)
* As that last sentence implies, the application reads
  to you using the AppleScript `say` command.

Runs on Mac OS X 10.9
---------------------

Although this application is written in Scala, with a little
Java, it only works on Mac OS X systems because (a) the text 
is spoken to you using the Mac AppleScript "say" command, 
and (b) I don't have any Windows computers.

I have written the application on Mac OS X 10.9. I don't know
for sure, but it should also run on OS X 10.7 and 10.8, though
I have know way of knowing that for sure; all of my systems run
10.9.

Why a Client and Server?
------------------------

In the beginning I did not have separate Client and Server
applications, but I made the Server a separate process (in a 
separate JVM) because the UI was unresponsive when I did the 
AppleScript say/speak stuff in the Client JVM. As a side 
benefit it helps to demo Akka Remote Actors, but the real 
motivation was to make the UI more responsive.

The Client Application
----------------------

The Client action starts in the _Client.scala_ class. That class
does very little setup work, then starts the Main controller.

Compiling and Running
---------------------

Once you check the code out from Github, you compile it with
this command:

    sbt compile

and run it with this command:

    sbt run

Note that the Client depends on the Server to be running. The Server
does the actual speaking, so if it's not running, your not going
to hear anything.

Building the Mac Executable
---------------------------

The current process of building a Mac OS X executable is a hack, but
it works. I use SBT with the sbt-assembly plugin to create one
application jar file, and then use Ant to build the Mac application.
While that's a hack, all you have to do is run the `assemble-run.sh`
script in the Client project root directory to build and run the
executable.

Akka
----

The Client and Server communicate using Akka Remote Actors.

The Server Jar File
-------------------

At this moment the Client expects the Server jar file to be named
_WikipediaReaderServer.jar_, and it needs to be in your 
_$HOME/Library/Application Support/alvinalexander.com/WikipediaReader_
directory. This situation is fluid, but that's how it works right now.

License
-------

This software is released under the licensing terms of the 
GNU GPL Version 3. See the accompanying LICENSE file for more
details.

Misc
----

Output logging goes to /var/log/WikipediaReader.log.















