package com.github.leosilvadev.mybus.web

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.github.leosilvadev.mybus.domains.Point
import com.typesafe.scalalogging.Logger
import io.vertx.scala.core.http.HttpServerRequest

import scala.util.Try

object Params {

  private val logger = Logger(Params.getClass.getName)

  def point(request: HttpServerRequest): Try[Point] = Try {
    val x = request.getParam("x").map(_.toLong).getOrElse(0L)
    val y = request.getParam("y").map(_.toLong).getOrElse(0L)
    val time = request.getParam("time").map(LocalTime.parse(_, DateTimeFormatter.ISO_LOCAL_TIME)).getOrElse(LocalTime.now())
    Point(x, y, time)
  }

  def lineName(request: HttpServerRequest) =
    request.getParam("lineName")


}
