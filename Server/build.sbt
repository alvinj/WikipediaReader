import AssemblyKeys._

// sbt-assembly
assemblySettings

name := "WikiReaderServer"
 
version := "0.1"
 
scalaVersion := "2.10.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

// "junit-interface" lets me use ScalaTest from within Eclipse.
// see http://www.scala-sbt.org/release/docs/Detailed-Topics/Testing.html
libraryDependencies ++= Seq(
    "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
    "com.novocode" % "junit-interface" % "0.9" % "test",
    "org.scala-lang" % "scala-swing" % "2.10+",
    "com.typesafe.akka" %% "akka-actor" % "2.1.1",
    "com.typesafe.akka" %% "akka-remote" % "2.1.1"
)

