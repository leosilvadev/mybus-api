package com.github.leosilvadev.mybus

import com.github.leosilvadev.mybus.domains.Stop

class City(points: Array[Array[Long]])

object City {

  private implicit class Crossable[X](xs: Traversable[X]) {
    def cross[Y](ys: Traversable[Y]) = for { x <- xs; y <- ys } yield (x, y)
  }

}