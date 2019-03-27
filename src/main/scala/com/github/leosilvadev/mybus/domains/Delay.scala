package com.github.leosilvadev.mybus.domains

import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.json.{Json, JsonObject}

case class Delay(lineName: String, minutes: Long)

object Delay {

  private val logger = Logger(Delay.getClass.getName)

  /**
    * Returns the a delay for a given line. When no delay for that line exists
    * a [[None]] will be returned.
    *
    * @param delays All the delays for all the lines
    * @param line The line which will be used for the delay search
    * @return the first delay found for the given line
    */
  def from(delays: List[Delay])(line: Line): Option[Delay] = {
    logger.debug("Searching for delay at line {} inside delays {}", line, delays)
    delays.find(_.lineName.toUpperCase() == line.name.toUpperCase())
  }

  def toJson(delay: Delay): JsonObject = Json.obj(
    ("line_name", delay.lineName),
    ("minutes", delay.minutes)
  )
}