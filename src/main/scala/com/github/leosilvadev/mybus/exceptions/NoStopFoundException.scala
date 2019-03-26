package com.github.leosilvadev.mybus.exceptions

import com.github.leosilvadev.mybus.domains.Point

class NoStopFoundException(point: Point) extends
  RuntimeException(s"No stop was found. [x: ${point.x}, y: ${point.y}, time: x: ${point.time}]")
