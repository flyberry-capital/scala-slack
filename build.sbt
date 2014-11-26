name := "scala-slack"

organization := "com.flyberry"

version := "0.1-SNAPSHOT"

scalacOptions += "-target:jvm-1.7"


libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.0-M1"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "0.3.16"

libraryDependencies += "org.mockito" % "mockito-core" % "1.8.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"