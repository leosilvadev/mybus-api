package com.github.leosilvadev.mybus.domains

import java.time.LocalTime

import org.scalatest.FunSpec

class ClosestStopTest extends FunSpec {

  describe("Find the closest stop") {

    it("Must find the closest stop when only one stop is available") {
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0))
      )

      val point = Point(4, 3, LocalTime.of(11, 0, 0))
      val closestStop = ClosestStop.from(stops)(point)

      assert(closestStop.isDefined)
      assert(closestStop.get.x == 0)
      assert(closestStop.get.y == 2)
      assert(closestStop.get.time == LocalTime.of(12, 0, 0))
    }

    it("Must not find any stop when no stop exist") {
      val stops = List()

      val point = Point(4, 3, LocalTime.of(11, 0, 0))
      val closestStop = ClosestStop.from(stops)(point)

      assert(closestStop.isEmpty)
    }

    it("Must not find any stop when no stop is available") {
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0))
      )

      val point = Point(4, 3, LocalTime.of(13, 0, 0))
      val closestStop = ClosestStop.from(stops)(point)

      assert(closestStop.isEmpty)
    }

    it("Must find the closest stop when the closest is still on time") {
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(2, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(3, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),
        StopTime(4, Stop(4, 6, 4), LocalTime.of(12, 7, 0)),
        StopTime(5, Stop(5, 6, 7), LocalTime.of(12, 9, 0)),
        StopTime(6, Stop(6, 8, 7), LocalTime.of(12, 11, 0)),
        StopTime(7, Stop(7, 10, 7), LocalTime.of(12, 13, 0)),
      )

      val point = Point(4, 3, LocalTime.of(12, 0, 0))
      val closestStop = ClosestStop.from(stops)(point)

      assert(closestStop.isDefined)
      assert(closestStop.get.x == 3)
      assert(closestStop.get.y == 2)
      assert(closestStop.get.time == LocalTime.of(12, 2, 0))
    }

    it("Must find the closest stop considering that the closest is passed") {
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(2, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(3, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),
        StopTime(4, Stop(4, 6, 4), LocalTime.of(12, 7, 0)),
        StopTime(5, Stop(5, 6, 7), LocalTime.of(12, 9, 0)),
        StopTime(6, Stop(6, 8, 7), LocalTime.of(12, 11, 0)),
        StopTime(7, Stop(7, 10, 7), LocalTime.of(12, 13, 0)),
      )

      val point = Point(4, 3, LocalTime.of(12, 10, 0))
      val closestStop = ClosestStop.from(stops)(point)

      assert(closestStop.isDefined)
      assert(closestStop.get.x == 8)
      assert(closestStop.get.y == 7)
      assert(closestStop.get.time == LocalTime.of(12, 11, 0))
    }

  }

}
