name := "mybus-api"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "io.vertx" %% "vertx-lang-scala-stack" % "3.6.3"
libraryDependencies += "io.vertx" %% "vertx-web-scala" % "3.6.3"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"