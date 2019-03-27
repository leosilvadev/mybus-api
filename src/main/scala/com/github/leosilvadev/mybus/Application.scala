package com.github.leosilvadev.mybus

import com.github.leosilvadev.mybus.config.ServerConfig
import com.github.leosilvadev.mybus.web.Server
import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx

import scala.util.{Failure, Success}

object Application extends App {

  val logger = Logger(Application.getClass.getName)

  val root = "/home/leosilvadev/dev/workspace/scala/mybus-api/src/main/resources/data/"
  val config = ServerConfig(
    s"$root/lines.csv",
    s"$root/stops.csv",
    s"$root/times.csv",
    s"$root/delays.csv",
    8081,
    1000
  )
  val vertx = Vertx.vertx()

  implicit val executionContext = VertxExecutionContext(vertx.getOrCreateContext())

  vertx.deployVerticleFuture(Server(config)) onComplete {
    case Success(_) => logger.info(s"Server running at port ${config.serverPort}")
    case Failure(ex) => logger.error(ex.getMessage, ex)
  }

}
