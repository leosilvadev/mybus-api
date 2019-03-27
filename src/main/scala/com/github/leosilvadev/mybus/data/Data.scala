package com.github.leosilvadev.mybus.data

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import com.github.leosilvadev.mybus.domains.{Delay, Line, Stop, StopTime}

object Data {

  def lines(path: String): List[Line] =
    DomainParse.parseFile(path, columns => Line(columns(0).toLong, columns(1)))

  def stops(path: String): List[Stop] =
    DomainParse.parseFile(path, columns => Stop(columns(0).toLong, columns(1).toLong, columns(2).toLong))

  def stopTimes(path: String)(stops: List[Stop]): List[StopTime] =
    DomainParse.parseFile(path, toStopTime(stops)).filterNot(_.isEmpty).map(_.get)

  def delays(path: String): List[Delay] =
    DomainParse.parseFile(path, toDelay).filterNot(_.isEmpty).map(_.get)

  private def toStopTime(stops: List[Stop])(columns: Array[String]) = for {
    stop <- stops.find(s => s.id == columns(1).toLong)
    stopTime <- Some(StopTime(columns(0).toLong, stop, LocalTime.parse(columns(2), DateTimeFormatter.ISO_LOCAL_TIME)))
  } yield stopTime

  private def toDelay(columns: Array[String]) = for {
    delay <- Some(Delay(columns(0), columns(1).toLong))
  } yield delay

}
