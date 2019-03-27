package com.github.leosilvadev.mybus.domains

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.json.{Json, JsonObject}

case class NextVehicle(line: Line, stop: Stop, time: LocalTime)

object NextVehicle {

  private val logger = Logger(NextVehicle.getClass.getName)

  /**
    * Returns the next vehicle that is going to arrive at the given point at given time.
    * A [[None]] will be returned in case there is no more vehicles at this stop at the given time.
    *
    * @param lines All the existent lines
    * @param stopTimes All the existent stop times
    * @param referencePoint The point that represents the stop where we want to look for the next vehicle
    * @return the next vehicle (if there is) at the given stop
    */
  def from(lines: List[Line], stopTimes: List[StopTime])(referencePoint: Point): Option[NextVehicle] = {
    logger.debug("Searching for the next vehicle at the {}. Lines: {} and Stop times: {}", referencePoint, lines, stopTimes)
    for {
      stopTime <- stopTimes.filter(_.time.isAfter(referencePoint.time)).find(stopsAtSameLocation(referencePoint))
      line <- lines.find(_.id == stopTime.lineId)
    } yield NextVehicle(line, stopTime.stop, stopTime.time)
  }

  def toJson(vehicle: NextVehicle): JsonObject = Json.obj(
    ("line", Line.toJson(vehicle.line)),
    ("stop", Stop.toJson(vehicle.stop)),
    ("time", vehicle.time.format(DateTimeFormatter.ISO_LOCAL_TIME))
  )

  private def stopsAtSameLocation(point: Point)(nextStopTime: StopTime) =
    point.x == nextStopTime.stop.x && point.y == nextStopTime.stop.y

}
