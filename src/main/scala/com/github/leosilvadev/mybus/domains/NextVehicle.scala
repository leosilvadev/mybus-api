package com.github.leosilvadev.mybus.domains

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import io.vertx.lang.scala.json.{Json, JsonObject}

case class NextVehicle(line: Line, stop: Stop, time: LocalTime)

object NextVehicle {

  def from(lines: List[Line], stopTimes: List[StopTime])(point: Point): Option[NextVehicle] = for {
    stopTime <- stopTimes.filter(stopsAtSameLocation(point)).find(_.time.isAfter(point.time))
    line <- lines.find(_.id == stopTime.lineId)
  } yield NextVehicle(line, stopTime.stop, stopTime.time)

  def toJson(vehicle: NextVehicle): JsonObject = Json.obj(
    ("line", Line.toJson(vehicle.line)),
    ("stop", Stop.toJson(vehicle.stop)),
    ("time", vehicle.time.format(DateTimeFormatter.ISO_LOCAL_TIME))
  )

  private def stopsAtSameLocation(point: Point)(nextStopTime: StopTime) =
    point.x == nextStopTime.stop.x && point.y == nextStopTime.stop.y

}
