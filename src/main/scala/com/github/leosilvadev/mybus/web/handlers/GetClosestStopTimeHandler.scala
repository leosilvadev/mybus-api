package com.github.leosilvadev.mybus.web.handlers

import java.time.LocalTime

import com.github.leosilvadev.mybus.NoStopFoundException
import com.github.leosilvadev.mybus.domains.{ClosestStop, Point, StopTime}
import com.github.leosilvadev.mybus.web.Params
import com.github.leosilvadev.mybus.web.Routes.formatter
import io.vertx.core.Handler
import io.vertx.lang.scala.json.Json
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
        context.response()
          .setStatusCode(200)
          .setChunked(true)
          .end(ClosestStop.toJson(stop).encode())

      case Failure(ex: NoStopFoundException) =>
        context.response()
          .setStatusCode(404)
          .setChunked(true)
          .end(Json.obj(("message", ex.getMessage)).encode())

      case Failure(ex) =>
        context.response()
          .setStatusCode(500)
          .end(Json.obj(("message", ex.getMessage)).encode())
    }
  }

  private def closestStopFrom(stopTimes: List[StopTime], point: Point): Try[ClosestStop] =
    ClosestStop.from(stopTimes)(point)
      .map(Success(_))
      .getOrElse(Failure(new NoStopFoundException(point)))
}
