package com.lunatech.lunabot

import javax.servlet.http.HttpServletRequest

import org.scalatra._
import scalate.ScalateSupport
import org.scalatra.test.specs2._

import scala.util.DynamicVariable

// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class LunabotServletSpec extends ScalatraSpec { def is =
  "GET / on LunabotServlet"                     ^
    "should return status 200"                  ! root200^
  "echoResp fuction"                            ^
    "should return the parameter as result"     ! echoResp("hello")^
                                                end

  addServlet(classOf[LunabotServlet], "/*")

  def root200 = get("/") {
    //page exists
    status must_== 200
  }

  def echoResp(bodyStr: String): Boolean = {
    val body = new LunabotServlet().printBody(bodyStr)
    body must_== bodyStr
  }

}
