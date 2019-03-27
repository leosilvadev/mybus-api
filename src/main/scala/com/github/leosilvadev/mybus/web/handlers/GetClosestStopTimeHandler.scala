package com.github.leosilvadev.mybus.web.handlers

import java.time.LocalTime

import com.github.leosilvadev.mybus.domains.{ClosestStop, Point, StopTime}
import com.github.leosilvadev.mybus.exceptions.NoStopFoundException
import com.github.leosilvadev.mybus.web.{Params, Response}
import io.vertx.core.Handler
import io.vertx.scala.ext.web.RoutingContext

import scala.util.{Failure, Success, Try}

case class GetClosestStopTimeHandler(stopTimes: List[StopTime]) extends Handler[RoutingContext] {

  override def handle(context: RoutingContext): Unit = {
    val request = context.request()

    val result = for {
      point <- Params.point(request)
      closest <- closestStopFrom(stopTimes, point)
    } yield closest

    result match {
      case Success(stop) =>
        Response.ok(context, ClosestStop.toJson(stop))

      case Failure(ex: NoStopFoundException) =>
        Response.notFound(context, ex.getMessage)

      case Failure(ex) =>
        Response.internalError(context, ex.getMessage)
    }
  }

  private def closestStopFrom(stopTimes: List[StopTime], point: Point): Try[ClosestStop] =
    ClosestStop.from(stopTimes)(point)
      .map(Success(_))
      .getOrElse(Failure(new NoStopFoundException(point)))
}
