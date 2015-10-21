package com.lunatech.lunabot


import dispatch.Req
import dispatch._, Defaults._

import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.JsonMethods

import org.slf4j.LoggerFactory


import org.scalatra.json._

trait HipChatResponder {

  def process(input: PreparedResponse): Unit

}

/**
 * HipChatResponder implementation using dispatch
 */
object DispatchHipChatResponder extends HipChatResponder {

  protected implicit val jsonFormats: Formats = DefaultFormats

  val logger = LoggerFactory.getLogger(getClass)

  /**
   * The workhorse, producing just side effects (it makes you wander what are the side effects of a horse ;)
   * @param input
   */
  def process(input: PreparedResponse): Unit = {
    input match {
      case Right((url, jValue)) => dispatch.Http(constructRequest(url.toString) << JsonMethods.compact(jValue))
      case Left(message) => logger.error(message)
    }

  }

  /*
    * Form the url where response should be sent by filling the right roomId and corresponding authToken from configuration
    * @param hipchatMsg The HipChatMessage object received by the post request to Lunabot
    * @return The request that is about to be sent with the right destination url
    *  */
  private def constructRequest(url: String): Req = {
    dispatch.url(url).setContentType("application/json", "UTF-8").POST
  }


}
