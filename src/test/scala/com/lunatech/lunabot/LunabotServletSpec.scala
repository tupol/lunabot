package com.lunatech.lunabot

import org.scalatra.test.specs2._

import scala.io.Source
import scala.util.{Success, Failure, Try}


// For more on Specs2, see http://etorreborre.github.com/specs2/guide/org.specs2.guide.QuickStart.html
class LunabotServletSpec extends ScalatraSpec {

  test =>

  // Test REPL implementation: returns back the same code
  // or throws an exception if the code is "THROW!"
  val testRepl: REPL = new REPL {
    def run(code: String): Try[String] = code match {
      case "THROW!" => Failure(new Exception)
      case _        => Success(code)
    }
  }

  def hook()
  // Test HipChatResponder
  val testResponder = new HipChatResponder {
    override def process(input: PreparedResponse): Unit = {
      test.servlet.response.getOutputStream.write(input.toString.toArray[Byte])
    }
  }

  val servlet = new LunabotServlet(Map(1 -> "token"), testRepl, testResponder)
  addServlet(servlet, "/*")



    def is =
      s2"""

  GET / on LunabotServlet
    return status 200       $root200

  POST /repl on LunabotServlet
    return status 500       $checkPostNoJsonStatus
    return status 500       $checkPostBadRoomIdStatus
    return status 500       $checkPostJsonIncompleteStatus
    return status 500       $checkPostJsonThrowExceptionStatus
    return status 200       $checkPostJsonGoodStatus
    return status 200       $checkPostJsonGoodBody

  LunabotServlet.generateResult
    return wrapped result from REPL    $checkGenerateNormalResult
    return wrapped exception from REPL    $checkGenerateResultWithException

  """




  def root200 = get("/") {
    //page exists
    status must_== 200
  }

  def checkPostNoJsonStatus = post("/repl") {
    //bad request
    status must_== 500
  }

  def checkPostJsonGoodStatus = post("/repl", jsonSampleGood) {
    //normal reply
    status must_== 200
  }

  def checkPostJsonGoodBody = post("/repl", jsonSampleGood) {
    //normal reply
    println("### " + body)
    body must contain ("x")
  }

  def checkPostJsonThrowExceptionStatus = post("/repl", jsonSampleThrowException) {
    //normal reply, containing the exception message
    status must_== 200
  }

  def checkPostBadRoomIdStatus = post("/repl", jsonSampleInvalidRoomId) {
    //wrong room, no cigar
    status must_== 200
  }

  def checkPostJsonIncompleteStatus = post("/repl", jsonSampleIncomplete) {
    //bad request (incomplete/invalid json body)
    status must_== 500
  }
  
  val jsonSampleGood = Source
    .fromInputStream(getClass.getResourceAsStream("/json_good.txt"))
    .getLines.mkString("\n")

  val jsonSampleIncomplete = Source
    .fromInputStream(getClass.getResourceAsStream("/json_bad.txt"))
    .getLines.mkString("\n")

  val jsonSampleInvalidRoomId = Source
    .fromInputStream(getClass.getResourceAsStream("/json_bad_room.txt"))
    .getLines.mkString("\n")

  val jsonSampleThrowException = Source
    .fromInputStream(getClass.getResourceAsStream("/json_throw.txt"))
    .getLines.mkString("\n")


}
