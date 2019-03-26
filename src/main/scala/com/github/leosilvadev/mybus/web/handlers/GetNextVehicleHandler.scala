package com.github.leosilvadev.mybus.web.handlers

import com.github.leosilvadev.mybus.domains._
import com.github.leosilvadev.mybus.exceptions.{NoStopFoundException, NoVehicleFoundException}
import com.github.leosilvadev.mybus.web.{Params, Response}
import com.typesafe.scalalogging.Logger
import io.vertx.core.Handler
import io.vertx.scala.ext.web.RoutingContext

import scala.util.{Failure, Success, Try}

case class GetNextVehicleHandler(lines: List[Line], stopTimes: List[StopTime]) extends Handler[RoutingContext] {

  private val logger = Logger(classOf[GetNextVehicleHandler])

  override def handle(context: RoutingContext): Unit = {
    val request = context.request()

    val result = for {
      point <- Params.point(request)
      nextVehicle <- nextVehicleFrom(stopTimes, point)
    } yield nextVehicle

    result match {
      case Success(vehicle) =>
        Response.ok(context, NextVehicle.toJson(vehicle))

      case Failure(ex: NoStopFoundException) =>
        Response.notFound(context, ex.getMessage)

      case Failure(ex) =>
        Response.internalError(context, ex.getMessage)
    }
  }

  private def nextVehicleFrom(stopTimes: List[StopTime], point: Point): Try[NextVehicle] = {
    logger.debug("Looking for next vehicle at point {} in {}", point, stopTimes)
    NextVehicle.from(lines, stopTimes)(point)
      .map(Success(_))
      .getOrElse(Failure(new NoVehicleFoundException(point)))
  }
}
