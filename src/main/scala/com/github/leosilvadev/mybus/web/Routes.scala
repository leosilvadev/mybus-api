package com.github.leosilvadev.mybus.web

import java.time.format.DateTimeFormatter

import com.github.leosilvadev.mybus.domains.StopTime
import com.github.leosilvadev.mybus.web.handlers.GetClosestStopTimeHandler
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router

object Routes {

  def formatter = DateTimeFormatter.ISO_LOCAL_TIME

  def all(vertx: Vertx, stopTimes: List[StopTime]): Router = {
    val router = Router.router(vertx)
    router.get("/api/v1/stops/:x/:y").handler(GetClosestStopTimeHandler(stopTimes))
    router
  }

}
