package com.github.leosilvadev.mybus.domains

import io.vertx.lang.scala.json.{Json, JsonObject}

case class Line(id: Long,
                name: String,
                stopTimes: List[StopTime] = List(),
                delays: List[Delay] = List())

object Line {
  def toJson(line: Line): JsonObject = Json.obj(
    ("id", line.id),
    ("name", line.name)
  )
}
