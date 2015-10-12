package com.lunatech.lunabot

import org.scalatra._
import scalate.ScalateSupport

class LunabotServlet extends LunabotStack {

  get("/") {
    <html>
      <body>
        <p>Hello lunabot!</p>
      </body>
    </html>
  }

  post("/repl") {
    //curl --request POST --data "curl --request POST --data "hcUser=maria&msg=Hello&room=Lunatech" http://localhost:8080/repl=maria&message=Hello&room=Lunatech" http://localhost:8080/repl
    printBody(request.body)
  }

  def printBody(reqBody: String): String = reqBody

}
