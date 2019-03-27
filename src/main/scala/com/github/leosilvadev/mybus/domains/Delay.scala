package com.github.leosilvadev.mybus.domains

import io.vertx.lang.scala.json.Json

case class Delay(lineName: String, minutes: Long)

object Delay {
  def toJson(delay: Delay) = Json.obj(
    ("line_name", delay.lineName),
    ("minutes", delay.minutes)
  )

  def from(delays: List[Delay])(line: Line): Option[Delay] =
    delays.find(_.lineName.toUpperCase() == line.name.toUpperCase())
}