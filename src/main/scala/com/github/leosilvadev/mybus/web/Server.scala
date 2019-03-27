package com.github.leosilvadev.mybus.web

import com.github.leosilvadev.mybus.Builder
import com.github.leosilvadev.mybus.config.ServerConfig
import io.vertx.lang.scala.ScalaVerticle

import scala.concurrent.Future

case class Server(serverConfig: ServerConfig) extends ScalaVerticle {

  override def startFuture(): Future[_] = Future {
    val lines = Builder.lines(serverConfig.linesFilePath)
    val stops = Builder.stops(serverConfig.stopsFilePath)
    val delays = Builder.delays(serverConfig.delaysFilePath)
    val stopTimes = Builder.stopTimes(serverConfig.timesFilePath)(stops)

    val router = RouterBuilder(vertx, serverConfig).build(lines, stopTimes, delays)

    vertx.createHttpServer()
      .requestHandler(router.accept(_))
      .listenFuture(serverConfig.serverPort)
  }

}
