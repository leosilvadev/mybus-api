package com.github.leosilvadev.mybus.domains

case class Delay(lineName: String, minutes: Long)

object Delay {
  def from(delays: List[Delay])(line: Line): Option[Delay] =
    delays.find(_.lineName == line.name)
}