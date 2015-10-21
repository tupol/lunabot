package com.lunatech.lunabot

import java.net.URL
import javax.servlet.http.HttpServletRequest

import com.lunatech.lunabot.model._
import org.scalatra._
import org.json4s.{DefaultFormats, Formats}
import org.slf4j.LoggerFactory
import org.scalatra.json._
import scala.util.{Failure, Success, Try}
import org.json4s._
import org.json4s.JsonDSL._

/**
 * Application to handle POST requests received from HipChat.
 *
 * A HipChat request contains information in JSON format which includes
 * a piece of code to be executed in scala REPL in asynchronous mode.
 * Once the response is sent by REPL, an POST request including the
 * scala result is sent to HipChat.
 */
class LunabotServlet(val tokens: Map[Int, String], val repl: REPL, val responder: HipChatResponder)
  extends ScalatraServlet with JacksonJsonSupport {

  val logger = LoggerFactory.getLogger(getClass)

  // Sets up automatic case class to JSON output serialization, required by
  // the JValueResult trait.
  protected implicit val jsonFormats: Formats = DefaultFormats

  private val COLOR_PARAM: String = "color"
  private val MESSAGE_PARAM: String = "message"
  private val MESSAGE_FORMAT: String =  "message_format"
  private val NOTIFY_PARAM: String = "notify"

  // Before every action runs, set the content type to be in JSON format.
  before() {
    contentType = formats("json")
  }

  get("/") {
    "Hello Lunabot!"
  }

  post("/repl") {

    responder.process(generateResult(request))

  }

  /**
   *
   */
  private def generateResult(request: HttpServletRequest) : PreparedResponse = {

    logger.debug(request.body)

    val jsonValue: JValue = parse(request.body.replace("mention_name", "mentionName"))
    val hipChatMessage: HipChatMessage = jsonValue.extract[HipChatMessage]
    val cmd: String = hipChatMessage.item.message.message.replace("/scala ", "")

    logger.debug(s"Processing $cmd")

    /**
     * Generate an Either result that can be used for constructing the post result
     * @param cmd
     * @param roomId
     * @return
     */
    def generateResult(cmd: String, roomId: Int): PreparedResponse = {
      lazy val triedResponse: Try[JValue] = evaluateExpression(cmd)
      val maybeResponse: Option[URL] = responseUrl(roomId)

      val maybeResult: PreparedResponse = maybeResponse match {
        case Some(url) =>
          triedResponse match {
            case Success(value) => Right(url, value)
            case Failure(ex) =>
              Right(url, responseJValue(s"Sorry your scala code has failed with ${ex.getMessage}", "red"))
          }
        case None =>
          val message: String = s"Unable to find the token for the room ${roomId}"
          Left(message)

      }
      maybeResult
    }

    generateResult(cmd, hipChatMessage.item.room.id)
  }

  /*
   * Creates the url where Lunabot sends its POST request
   * @param hipchatMsg The message that is included in the post request received by Lunabot
   */
  private def responseUrl(roomId: Int): Option[URL] = tokens.get(roomId).map {
    t => new URL(s"https://api.hipchat.com/v2/room/$roomId/notification?auth_token=$t")
  }


  private def evaluateExpression(expression: String): Try[JValue] = repl.run(expression).map {
    responseJValue(_, "green")
  }

  private def responseJValue(message: String, color: String, notify: Boolean = false, messageFormat: String = "text"): JValue =
    render((COLOR_PARAM -> color) ~
      (MESSAGE_PARAM -> message) ~
      (NOTIFY_PARAM -> false) ~
      (MESSAGE_FORMAT -> messageFormat))

}
