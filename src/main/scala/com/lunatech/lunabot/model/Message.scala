package com.lunatech.lunabot.model

/**
 * This class is the model representation of the JSON object message
 */
case class Message(date: String, from: FromInfo, id: String, mentions: List[Any], message: String, `type`: String)
