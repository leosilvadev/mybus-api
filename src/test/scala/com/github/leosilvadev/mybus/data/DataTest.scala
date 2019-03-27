package com.github.leosilvadev.mybus.data

import java.time.LocalTime

import com.github.leosilvadev.mybus.domains.{Delay, Line, Stop, StopTime}
import org.scalatest.FunSpec

class DataTest extends FunSpec {

  describe("Read all data from files") {
    it("Must read all the delays") {
      val delays = Data.delays("data/delays.csv")

      assert(delays.size == 3)
      assert(delays.head == Delay("M4", 1))
      assert(delays(1) == Delay("200", 2))
      assert(delays(2) == Delay("S75", 10))
    }

    it("Must read all the lines") {
      val lines = Data.lines("data/lines.csv")

      assert(lines.size == 3)
      assert(lines.head == Line(0, "M4"))
      assert(lines(1) == Line(1, "200"))
      assert(lines(2) == Line(2, "S75"))
    }

    it("Must read all the stops") {
      val stops = Data.stops("data/stops.csv")

      assert(stops.size == 12)
      assert(stops.head == Stop(0,1,1))
      assert(stops(1) == Stop(1,1,4))
      assert(stops(2) == Stop(2,1,7))
      assert(stops(3) == Stop(3,2,9))
      assert(stops(4) == Stop(4,3,11))
      assert(stops(5) == Stop(5,3,1))
      assert(stops(6) == Stop(6,3,4))
      assert(stops(7) == Stop(7,3,7))
      assert(stops(8) == Stop(8,1,10))
      assert(stops(9) == Stop(9,2,12))
      assert(stops(10) == Stop(10,4,9))
      assert(stops(11) == Stop(11,5,7))
    }

    it("Must read all the stop times") {
      val stops = Data.stops("data/stops.csv")
      val stopTimes = Data.stopTimes("data/times.csv")(stops)

      assert(stopTimes.size == 15)
      assert(stopTimes.head == StopTime(0, Stop(0,1,1), LocalTime.of(10, 0)))
      assert(stopTimes(1) == StopTime(0, Stop(1,1,4), LocalTime.of(10, 2)))
      assert(stopTimes(2) == StopTime(0, Stop(2,1,7), LocalTime.of(10, 5)))
      assert(stopTimes(3) == StopTime(0, Stop(3,2,9), LocalTime.of(10, 7)))
      assert(stopTimes(4) == StopTime(0, Stop(4,3,11), LocalTime.of(10, 9)))

      assert(stopTimes(5) == StopTime(1, Stop(5,3,1), LocalTime.of(10, 1)))
      assert(stopTimes(6) == StopTime(1, Stop(6,3,4), LocalTime.of(10, 4)))
      assert(stopTimes(7) == StopTime(1, Stop(7,3,7), LocalTime.of(10, 6)))
      assert(stopTimes(8) == StopTime(1, Stop(3,2,9), LocalTime.of(10, 8)))
      assert(stopTimes(9) == StopTime(1, Stop(8,1,10), LocalTime.of(10, 10)))

      assert(stopTimes(10) == StopTime(2, Stop(3,2,9), LocalTime.of(10, 8)))
      assert(stopTimes(11) == StopTime(2, Stop(9,2,12), LocalTime.of(10, 9)))
      assert(stopTimes(12) == StopTime(2, Stop(4,3,11), LocalTime.of(10, 11)))
      assert(stopTimes(13) == StopTime(2, Stop(10,4,9), LocalTime.of(10, 13)))
      assert(stopTimes(14) == StopTime(2, Stop(11,5,7), LocalTime.of(10, 15)))
    }
  }

}
