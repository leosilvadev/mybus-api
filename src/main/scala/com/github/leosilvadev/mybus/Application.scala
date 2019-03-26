package com.github.leosilvadev.mybus

import com.github.leosilvadev.mybus.web.Routes
import io.vertx.lang.scala.VertxExecutionContext
import io.vertx.scala.core.Vertx

import scala.util.{Failure, Success}

object Application extends App {

  val root = "/home/leosilvadev/dev/workspace/scala/mybus-api/src/main/resources/data/"
  val lines = Builder.lines(s"$root/lines.csv")
  val stops = Builder.stops(s"$root/stops.csv")

  val delays = Builder.delays(s"$root/delays.csv")
  val stopTimes = Builder.stopTimes(s"$root/times.csv")(stops)

  val vertx = Vertx.vertx()

  protected implicit val executionContext = VertxExecutionContext(vertx.getOrCreateContext())

  val router = Routes.all(vertx, stopTimes)

  vertx.createHttpServer()
    .requestHandler(router.accept(_))
    .listenFuture(8081).onComplete {
      case Success(_) => println("Server running")
      case Failure(ex) => println(ex)
    }

}
