package com.github.leosilvadev.mybus.domains

import java.time.LocalTime

case class NextVehicle(line: Line, stop: Stop, time: LocalTime)

object NextVehicle {

  def from(lines: List[Line], stopTimes: List[StopTime])(stop: Stop, time: LocalTime): Option[NextVehicle] = for {
    stopTime <- stopTimes.filter(stopsAtSameLocation(stop)).find(_.time.isAfter(time))
    line <- lines.find(_.id == stopTime.lineId)
  } yield NextVehicle(line, stopTime.stop, stopTime.time)

  private def stopsAtSameLocation(stop: Stop)(nextStopTime: StopTime) =
    stop.x == nextStopTime.stop.x && stop.y == nextStopTime.stop.y

}
