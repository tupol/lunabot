package com.lunatech.lunabot

import com.lunatech.lunabot.model._
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.json4s.JsonDSL._
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
    log(request.body)
    val jsonValue = parse(request.body.replace("mention_name", "mentionName"))
    val hipchatMsg = jsonValue.extract[HipChatMessage]
    val replResponse = "Response from REPL"
    ("color" -> "red") ~
      ("message" -> replResponse) ~
      ("notify" -> false) ~
      ("message_format" -> "text")
  }
}
