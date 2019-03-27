package com.github.leosilvadev.mybus.data

import scala.io.Source

protected object DomainParse {

  type Columns = Array[String]

  def parseFile[T](path: String, doParse: Columns => T): List[T] =
    Source.fromResource(path).getLines.toList.tail.map(l => doParse(l.split(",")))

}

