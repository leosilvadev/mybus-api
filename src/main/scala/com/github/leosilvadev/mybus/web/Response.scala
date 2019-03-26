package com.github.leosilvadev.mybus.web

import io.vertx.lang.scala.json.{Json, JsonObject}
import io.vertx.scala.core.http.HttpServerResponse
import io.vertx.scala.ext.web.RoutingContext

object Response {

  def ok(context: RoutingContext, json: JsonObject = Json.emptyObj()): Unit = {
    responseFor(context)
      .setStatusCode(200)
      .end(json.encode())
  }

  def notFound(context: RoutingContext, message: String = "Not found"): Unit = {
    responseFor(context)
      .setStatusCode(404)
      .end(Json.obj(("message", message)).encode())
  }

  def internalError(context: RoutingContext, ex: String = "Internal server error"): Unit = {
    responseFor(context)
      .setStatusCode(500)
      .end(Json.obj(("message", ex)).encode())
  }

  private def responseFor(context: RoutingContext): HttpServerResponse = {
    context.response()
      .putHeader("Content-Type", "application/json; charset=utf-8")
      .setChunked(true)
  }

}
