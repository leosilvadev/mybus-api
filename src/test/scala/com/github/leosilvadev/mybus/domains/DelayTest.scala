package com.github.leosilvadev.mybus.domains

import org.scalatest.FunSpec

class DelayTest extends FunSpec {

  describe("Delay of a line") {

    it("Must not find a delay when no one exist for the given line") {
      val delays = List(
        Delay("140", 2),
        Delay("247", 5),
        Delay("M4", 1)
      )

      val line = Line(1, "M2")

      val delay = Delay.from(delays)(line)

      assert(delay.isEmpty)
    }

    it("Must find a delay when only one exist for the given line") {
      val delays = List(
        Delay("140", 2),
        Delay("247", 5),
        Delay("M4", 1)
      )

      val line = Line(1, "M4")

      val delay = Delay.from(delays)(line)

      assert(delay.isDefined)
      assert(delay.get.minutes == 1)
    }

    it("Must find the first delay when more one exist for the given line") {
      val delays = List(
        Delay("140", 2),
        Delay("247", 5),
        Delay("M4", 1),
        Delay("140", 3),
        Delay("247", 4)
      )

      val line = Line(1, "247")

      val delay = Delay.from(delays)(line)

      assert(delay.isDefined)
      assert(delay.get.minutes == 5)
    }

  }

}
