name := "scala-slack"

organization := "com.flyberrycapital"

version := "0.3.0"

scalacOptions += "-target:jvm-1.6"

scalaVersion := "2.11.2"

crossScalaVersions ++= Seq("2.10.4", "2.11.2")


// Publish settings

publishMavenStyle := true

publishTo := {
   val nexus = "https://oss.sonatype.org/"
   if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
   else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/flyberry-capital/scala-slack"))

pomExtra := (
   <scm>
      <url>git@github.com:flyberry-capital/scala-slack.git</url>
      <connection>git@github.com:flyberry-capital/scala-slack.git</connection>
   </scm>
      <developers>
         <developer>
            <id>ksolan</id>
            <name>Kyle Solan</name>
            <url>https://github.com/ksolan</url>
         </developer>
      </developers>)



libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.0-M1"

libraryDependencies += "org.scalaj" %% "scalaj-http" % "1.1.0"

libraryDependencies += "org.mockito" % "mockito-core" % "1.8.5" % "test"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"
