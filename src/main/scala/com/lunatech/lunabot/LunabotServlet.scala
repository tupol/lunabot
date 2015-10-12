package com.lunatech.lunabot

import com.lunatech.lunabot.model._
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._

class LunabotServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  before() {
    contentType = formats("json")
  }

  get("/") {
    "Hello Lunabot!"
  }

  post("/repl") {
    printBody(request.body)
    val hce = parse(request.body)
    val json = hce.extract[HipChatMessage]
    json.toString
  }

  def printBody(reqBody: String): String = reqBody

}