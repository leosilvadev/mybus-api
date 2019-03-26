package com.github.leosilvadev.mybus.domains

import java.time.LocalTime

case class StopTime(lineId: Long, stop: Stop, time: LocalTime)
