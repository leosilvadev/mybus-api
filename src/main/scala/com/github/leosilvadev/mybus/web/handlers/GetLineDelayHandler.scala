package com.github.leosilvadev.mybus.web.handlers

import com.github.leosilvadev.mybus.domains.{Delay, Line}
import com.github.leosilvadev.mybus.web.{Params, Response}
import io.vertx.core.Handler
import io.vertx.scala.ext.web.RoutingContext

case class GetLineDelayHandler(lines: List[Line], delays: List[Delay]) extends Handler[RoutingContext] {
  override def handle(context: RoutingContext): Unit = {
    val result = for {
      lineName <- Params.lineName(context.request())
      line <- lines.find(_.name.toUpperCase() == lineName.toUpperCase())
      delay <- Delay.from(delays)(line)
    } yield delay

    result match {
      case Some(delay) =>
        Response.ok(context, Delay.toJson(delay))

      case None =>
        Response.notFound(context, "No delay found")
    }
  }
}
