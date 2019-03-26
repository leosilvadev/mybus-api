package com.github.leosilvadev.mybus.domains

import java.time.LocalTime

import org.scalatest.FunSpec

class NextVehicleTest extends FunSpec {

  describe("Find the next vehicle") {

    it("Must find the next vehicle when there is a next one") {
      val lines = List(Line(1, "142"))
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(1, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(1, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),
        StopTime(1, Stop(4, 6, 4), LocalTime.of(12, 7, 0)),
        StopTime(1, Stop(5, 6, 7), LocalTime.of(12, 9, 0)),
        StopTime(1, Stop(6, 8, 7), LocalTime.of(12, 11, 0)),
        StopTime(1, Stop(7, 10, 7), LocalTime.of(12, 13, 0)),
      )

      val point = Point(8, 7, LocalTime.of(12, 0, 0))
      val next = NextVehicle.from(lines, stops)(point)

      assert(next.isDefined)
      assert(next.get.stop.x == point.x)
      assert(next.get.stop.y == point.y)
      assert(next.get.line == lines.head)
      assert(next.get.time == LocalTime.of(12, 11, 0))
    }

    it("Must find the next vehicle when there is a next one (multiple lines colliding)") {
      val lines = List(Line(1, "142"), Line(2, "147"))
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(1, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(1, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),

        StopTime(2, Stop(4, 3, 0), LocalTime.of(12, 0, 0)),
        StopTime(2, Stop(5, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(2, Stop(6, 3, 5), LocalTime.of(12, 5, 0)),
      )

      val point = Point(3, 2, LocalTime.of(12, 0, 0))
      val next = NextVehicle.from(lines, stops)(point)

      assert(next.isDefined)
      assert(next.get.stop.x == point.x)
      assert(next.get.stop.y == point.y)
      assert(next.get.line == lines.head)
      assert(next.get.time == LocalTime.of(12, 2, 0))
    }

    it("Must find the next vehicle when previous vehicles already passed") {
      val lines = List(Line(1, "142"), Line(2, "147"))
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(1, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(1, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),

        StopTime(2, Stop(4, 3, 0), LocalTime.of(12, 3, 0)),
        StopTime(2, Stop(5, 3, 2), LocalTime.of(12, 5, 0)),
        StopTime(2, Stop(6, 3, 5), LocalTime.of(12, 7, 0)),
      )

      val point = Point(3, 2, LocalTime.of(12, 4, 0))
      val next = NextVehicle.from(lines, stops)(point)

      assert(next.isDefined)
      assert(next.get.stop == Stop(5, 3, 2))
      assert(next.get.line == lines(1))
      assert(next.get.time == LocalTime.of(12, 5, 0))
    }

    it("Must not find a next vehicle when the time is too late") {
      val lines = List(Line(1, "142"))
      val stops = List(
        StopTime(1, Stop(1, 0, 2), LocalTime.of(12, 0, 0)),
        StopTime(1, Stop(2, 3, 2), LocalTime.of(12, 2, 0)),
        StopTime(1, Stop(3, 6, 2), LocalTime.of(12, 5, 0)),
        StopTime(1, Stop(4, 6, 4), LocalTime.of(12, 7, 0)),
        StopTime(1, Stop(5, 6, 7), LocalTime.of(12, 9, 0)),
        StopTime(1, Stop(6, 8, 7), LocalTime.of(12, 11, 0)),
        StopTime(1, Stop(7, 10, 7), LocalTime.of(12, 13, 0)),
      )

      val point = Point(8, 7, LocalTime.of(12, 14, 0))
      val next = NextVehicle.from(lines, stops)(point)

      assert(next.isEmpty)
    }

  }

}
