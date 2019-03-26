package com.github.leosilvadev.mybus.exceptions

import com.github.leosilvadev.mybus.domains.Point

class NoVehicleFoundException(point: Point) extends
  RuntimeException(s"No vehicle was found. [x: ${point.x}, y: ${point.y}, time: x: ${point.time}]")
