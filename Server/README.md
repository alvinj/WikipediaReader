Wikipedia Reader: Server Component
==================================

This is the "server" component of my Wikipedia "reader" application.

If you're not a developer, there's nothing to see in this project.

If you are a developer, see the README files in the Client portion
of this application for more information.

Building the Server
-------------------

One thing I'll say about the Server code is that I build and run it
using the shell scripts in the root directory of this project:

* _assemble-run.sh_ builds a single jar file with sbt-assembly and then
  runs that jar file
* _package.sh_ builds the single jar file
* _run-jars.sh_ runs the existing jar file

Those jar files need to be refactored because they have redundant
information in them, but they do work.

License
-------

This software is released under the licensing terms of the 
GNU GPL Version 3. See the accompanying LICENSE file for more
details.



