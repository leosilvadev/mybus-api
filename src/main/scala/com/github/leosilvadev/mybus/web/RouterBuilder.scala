package com.github.leosilvadev.mybus.web

import com.github.leosilvadev.mybus.config.ServerConfig
import com.github.leosilvadev.mybus.domains.{Delay, Line, StopTime}
import com.github.leosilvadev.mybus.web.handlers.{GetClosestStopTimeHandler, GetLineDelayHandler, GetNextVehicleHandler}
import io.vertx.scala.core.Vertx
import io.vertx.scala.ext.web.Router
import io.vertx.scala.ext.web.handler.{LoggerHandler, TimeoutHandler}

case class RouterBuilder(vertx: Vertx, serverConfig: ServerConfig) {

  def build(lines: List[Line], stopTimes: List[StopTime], delays: List[Delay]): Router = {
    val router = Router.router(vertx)

    router.route().handler(LoggerHandler.create())
    router.get().handler(TimeoutHandler.create(serverConfig.endpointTimeout))
    router.get("/api/v1/coordinates/:x/:y/stops/next")
      .handler(GetClosestStopTimeHandler(stopTimes))

    router.get("/api/v1/coordinates/:x/:y/vehicles/next")
      .handler(GetNextVehicleHandler(lines, stopTimes))

    router.get("/api/v1/lines/:lineName/delays")
      .handler(GetLineDelayHandler(lines, delays))

    router
  }

}
