package com.lunatech.lunabot

import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class LunabotServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }

  get("/") {
    HipChat.all
  }

  post("/repl") {
    printBody(request.body)
  }

  def printBody(reqBody: String): String = reqBody

}

case class HipChat(name: String, msg: String)

object HipChat {
  var all = List(
    HipChat("Maria", "Hello lunabot"),
    HipChat("Dimitrios", "I am Dimitrios"),
    HipChat("Howard", "Hello, I am Howard"))
}