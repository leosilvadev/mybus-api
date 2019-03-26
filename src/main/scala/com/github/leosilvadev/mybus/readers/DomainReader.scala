package com.github.leosilvadev.mybus.readers

import scala.io.Source

object DomainReader {

  type Columns = Array[String]

  def read[T](path: String, doParse: Columns => T): List[T] =
    Source.fromFile(path).getLines.toList.tail.map(l => doParse(l.split(",")))

}

