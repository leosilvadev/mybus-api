package com.github.leosilvadev.mybus

import com.github.leosilvadev.mybus.config.ServerConfig
import com.github.leosilvadev.mybus.web.Server
import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx

import scala.util.{Failure, Success}

object Application extends App {

  val logger = Logger(Application.getClass.getName)

  val config = ServerConfig(
    "data/lines.csv",
    "data/stops.csv",
    "data/times.csv",
    "data/delays.csv",
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
