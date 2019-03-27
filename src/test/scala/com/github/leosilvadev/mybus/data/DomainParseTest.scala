package com.github.leosilvadev.mybus.data

import org.scalatest.FunSpec

class DomainParseTest extends FunSpec {

  describe("Parse file content") {

    it("Must read all the lines and columns, ignoring the header line") {
      val lines = DomainParse.parseFile("delays.csv", columns => columns)

      assert(lines.size == 3)
      assert(lines.head.length == 2)
      assert(lines(1).length == 2)
      assert(lines(2).length == 2)
    }

  }

}
