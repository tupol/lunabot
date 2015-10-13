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
    val jsonValue = parse(request.body.replace("mention_name","mentionName"))
    val hipchatMsg = jsonValue.extract[HipChatMessage]
    //Here Lunabot will connect with REPL to execute the scala code
    HipChatResponse("green","This should be replaced by the result from REPL", false, "text")
  }

  def printBody(reqBody: String): String = reqBody

}
