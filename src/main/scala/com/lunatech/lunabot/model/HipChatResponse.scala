package com.lunatech.lunabot.model

/**
 * Model representation of the JSON object to be sent to HipChat
 */
case class HipChatResponse (color: String, msg: String, notifyHipChat: Boolean, messageFormat: String)
