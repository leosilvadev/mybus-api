package com.github.leosilvadev.mybus.domains

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.typesafe.scalalogging.Logger
import io.vertx.lang.scala.json.{Json, JsonObject}

case class ClosestStop(x: Long, y: Long, distance: Double, time: LocalTime) extends Localizable

object ClosestStop {

  private val logger = Logger(ClosestStop.getClass.getName)

  /**
    * Returns the Closest stop given a list of available stop times and the actual customer point
    * The Closest stop means the stop that closer (considering a cartesian plane) to the given customer point at the given time
    *
    * @param stopTimes All the stop times in the cartesian plane
    * @param referencePoint The reference point from where we are looking the closest stop
    * @return the closest stop containing the exact point in the cartesian plane (x and y),
    *         the stop time and the distance from the reference point.
    */
  def from(stopTimes: List[StopTime])(referencePoint: Point): Option[ClosestStop] = {
    logger.debug("Searching for closes stop time for {} inside {}", referencePoint, stopTimes)
    val possibleStopTimes = stopTimes.filter(_.time.isAfter(referencePoint.time))
    if (possibleStopTimes.isEmpty) return None

    val firstStop = possibleStopTimes.head
    var bestStopTime = ClosestStop(firstStop.stop.x, firstStop.stop.y, distanceOf(referencePoint, firstStop.stop), firstStop.time)
    from(bestStopTime, firstStop, possibleStopTimes.tail)(referencePoint)
  }

  def toJson(closestStop: ClosestStop): JsonObject = Json.obj(
    ("x", closestStop.x),
    ("y", closestStop.y),
    ("distance", closestStop.distance),
    ("time", closestStop.time.format(DateTimeFormatter.ISO_LOCAL_TIME))
  )

  private def from(currentBestStopTime: ClosestStop, currentStopTime: StopTime, nextStopTimes: List[StopTime])
                       (referencePoint: Point): Option[ClosestStop] = {
    val distance = distanceOf(referencePoint, currentStopTime.stop)
    val closestStop = if (distance < currentBestStopTime.distance)
      ClosestStop(currentStopTime.stop.x, currentStopTime.stop.y, distance, currentStopTime.time)
    else
      currentBestStopTime

    if (nextStopTimes.isEmpty) {
      return Some(currentBestStopTime)
    }

    from(closestStop, nextStopTimes.head, nextStopTimes.tail)(referencePoint)
  }

  private def distanceOf(first: Localizable, second: Localizable): Double =
    math.hypot(first.x() - second.x(), first.y() - second.y())

}
