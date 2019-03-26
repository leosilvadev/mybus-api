package com.github.leosilvadev.mybus.web

import java.time.format.DateTimeFormatter

import com.github.leosilvadev.mybus.domains.{Line, StopTime}
import com.github.leosilvadev.mybus.web.handlers.{GetClosestStopTimeHandler, GetNextVehicleHandler}
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router

object Routes {

  def formatter = DateTimeFormatter.ISO_LOCAL_TIME

  def all(vertx: Vertx, lines: List[Line], stopTimes: List[StopTime]): Router = {
    val router = Router.router(vertx)
    router.get("/api/v1/coordinates/:x/:y/stops/next").handler(GetClosestStopTimeHandler(stopTimes))
    router.get("/api/v1/coordinates/:x/:y/vehicles/next").handler(GetNextVehicleHandler(lines, stopTimes))
    router
  }

}
