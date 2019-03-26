package com.github.leosilvadev.mybus.domains

case class Line(id: Long,
                name: String,
                stopTimes: List[StopTime] = List(),
                delays: List[Delay] = List())
