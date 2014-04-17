Wikipedia Reader: Server To-Do List
===================================

* parse sentences better. currently fails on things like
  "Lt. Columbo" and "Ph.D." because it assumes that decimals
  separate lines. (See the Peter Falk and Martin Odersky
  Wikipedia pages for those specific cases.)
* fix the amazingly kludgy code in TextUtils::cleanWikipediaSentence
* move the "pause" to the Client
* fix WikipediaReaderUtils::getScriptEngine (Singleton?)
* replace `println` statements with logging

