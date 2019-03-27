package com.github.leosilvadev.mybus.web

import com.github.leosilvadev.mybus.config.ServerConfig
import com.github.leosilvadev.mybus.data.Data
import io.vertx.lang.scala.ScalaVerticle

import scala.concurrent.Future

case class Server(serverConfig: ServerConfig) extends ScalaVerticle {

  override def startFuture(): Future[_] = Future {
    val lines = Data.lines(serverConfig.linesFilePath)
    val stops = Data.stops(serverConfig.stopsFilePath)
    val delays = Data.delays(serverConfig.delaysFilePath)
    val stopTimes = Data.stopTimes(serverConfig.timesFilePath)(stops)

    val router = RouterBuilder(vertx, serverConfig).build(lines, stopTimes, delays)

    vertx.createHttpServer()
      .requestHandler(router.accept(_))
      .listenFuture(serverConfig.serverPort)
  }

}
