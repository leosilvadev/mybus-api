package com.github.leosilvadev.mybus.web

import com.github.leosilvadev.mybus.Builder
import com.github.leosilvadev.mybus.config.ServerConfig
import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.ScalaVerticle

import scala.concurrent.Future

case class Server(serverConfig: ServerConfig) extends ScalaVerticle {

  private val logger = Logger(classOf[Server])

  override def startFuture(): Future[_] = Future {
    val lines = Builder.lines(serverConfig.linesFilePath)
    val stops = Builder.stops(serverConfig.stopsFilePath)
    val delays = Builder.delays(serverConfig.delaysFilePath)
    val stopTimes = Builder.stopTimes(serverConfig.timesFilePath)(stops)

    val router = Routes.all(vertx, serverConfig, lines, stopTimes, delays)

    vertx.createHttpServer()
      .requestHandler(router.accept(_))
      .listenFuture(serverConfig.serverPort)
  }

}
