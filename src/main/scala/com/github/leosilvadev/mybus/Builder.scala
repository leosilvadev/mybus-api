package com.github.leosilvadev.mybus

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.github.leosilvadev.mybus.domains.{Delay, Line, Stop, StopTime}
import com.github.leosilvadev.mybus.readers.DomainReader

object Builder {

  private val formatter = DateTimeFormatter.ISO_LOCAL_TIME

  def lines(path: String): List[Line] =
    DomainReader.read(path, columns => Line(columns(0).toLong, columns(1)))

  def stops(path: String): List[Stop] =
    DomainReader.read(path, columns => Stop(columns(0).toLong, columns(0).toLong, columns(0).toLong))

  def stopTimes(path: String)(stops: List[Stop]): List[StopTime] =
    DomainReader.read(path, toStopTime(stops)).filterNot(_.isEmpty).map(_.get)

  def delays(path: String): List[Delay] =
    DomainReader.read(path, toDelay).filterNot(_.isEmpty).map(_.get)

  private def toStopTime(stops: List[Stop])(columns: Array[String]) = for {
    stop <- stops.find(s => s.id == columns(1).toLong)
    stopTime <- Some(StopTime(columns(0).toLong, stop, LocalTime.parse(columns(2), formatter)))
  } yield stopTime

  private def toDelay(columns: Array[String]) = for {
    delay <- Some(Delay(columns(0), columns(1).toLong))
  } yield delay

}
