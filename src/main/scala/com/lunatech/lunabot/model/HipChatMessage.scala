package com.lunatech.lunabot.model

/**
 * @author mariadominguez on 12/10/2015.
 */

case class HipChatMessage(name: String, msg: String)

object HipChatMessage {
  var all = List(
    HipChatMessage("Maria", "Hello lunabot"),
    HipChatMessage("Dimitrios", "I am Dimitrios"),
    HipChatMessage("Howard", "Hello, I am Howard"))
}
