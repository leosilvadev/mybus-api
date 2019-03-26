package com.github.leosilvadev.mybus.web

import java.time.LocalTime

import com.github.leosilvadev.mybus.domains.Point
import com.github.leosilvadev.mybus.web.Routes.formatter
import com.typesafe.scalalogging.Logger
import io.vertx.scala.core.http.HttpServerRequest

import scala.util.Try

object Params {

  private val logger = Logger("Params")

  def point(request: HttpServerRequest): Try[Point] = Try {
    val x = request.getParam("x").map(_.toLong).getOrElse(0L)
    val y = request.getParam("y").map(_.toLong).getOrElse(0L)
    val time = request.getParam("time").map(LocalTime.parse(_, formatter)).getOrElse(LocalTime.now())
    Point(x, y, time)
  }

}
