package com.github.leosilvadev.mybus.domains

import io.vertx.lang.scala.json.{Json, JsonObject}

case class Stop(id: Long, x: Long, y: Long) extends Localizable

object Stop {
  def toJson(stop: Stop): JsonObject = Json.obj(
    ("id", stop.id),
    ("x", stop.x),
    ("y", stop.y)
  )
}
