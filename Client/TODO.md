To-Do List
==========

* add a Help screen, especially to list available voices
  * preferable the Voice textfield will be a type-ahead field one day
* improve the process of starting and stopping the server
* server: improve the speaking (better delays, test)
* handle failure better:
    * Server may be down
    * Internet may be down
        * TODO: took forever to time out internet access was down
        * note: fails properly/immediately when the internet is turned off in the Mac menubar
    * Internet may be slow

Done
----

~ persist voice choices/settings
~ get multiple voices working
~ the `resumeSpeaking` method in the MainController breaks the singleVoice vs multipleVoices settings
~ get Play working
~ get Pause working
~ better VCR icons
~ get Resume working
~ let me delete URLs
~ let me switch from one url to another without having to restart
~ let me switch voices without having to restart
~ remember the voice stuff as a preference (last selected voice, or which voices to rotate)
~ get the VCR controls working

Demo
----
- dnd urls
- play
- pause
- switch voice

Later
-----

* Add a listener to the Voice selection combo box and save the preference
  at the time the selection is changed

