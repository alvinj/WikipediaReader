Wikipedia Reader
================

As its name implies, the Wikipedia Reader reads Wikipedia web
pages to you. It runs on Mac OS X 10.9, and may also run on
10.7 and 10.8 (I don't know for sure, I only have 10.9 systems).

It's still very much a work in progress, but the basic 
functionality is here:

* It reads Wikipedia pages.
* You can drag and drop Wikipedia URLs onto the reader to add them to the list.
* The VCR controls usually work. :)
* You can configure the system to read pages to you in (a) one voice,
  or (b) multiple voices.
* You can delete URLs.
* The system remembers your URLs and voice settings (usually).

To use the reader, select a URL, press Play, and listen.

Voices
------

The WikipediaReader can use one or more voices. I recommend using the 
"Alex" voice when you first start because (a) it's a good voice and
(b) it's a standard voice on English Mac OS X systems.

That being said, I prefer hearing these British voices:

* Kate
* Oliver
* Serena

You can learn more about what voices are available on your Mac by:

1. Clicking the Apple icon
2. Selecting 'System Preferences'
3. Clicking 'Dictation and Speech' (fourth row down)
4. Clicking the 'Text to Speech' tab
5. Looking at the voices available under 'System Voice'

If you're going to use the WikipediaReader a lot, it will help to
(a) get to know these voices and (b) download some of the better
voices. (Only a few voices are on your system by default. If you want
to hear Kate, Oliver, etc., you need to download them. It's a free
download, but because the files are large, Apple doesn't ship
them automatically.)

Download
--------

I'll add a 'download' link here soon.

Developers
----------

If you're a developer, it may help to know that WikipediaReader is 
written in Scala, with a wee bit of Java, and also uses Akka Remote
Actors. The reading of the web pages is performed using the 
AppleScript 'say' command.

Background
----------

This Wikipedia Reader project spawned from my ambitious SARAH
project. You can learn more about SARAH at http://alvinalexander.com/sarah

License
-------

This software is released under the licensing terms of the 
GNU GPL Version 3. See the accompanying LICENSE file for more
details.



