package com.lunatech.lunabot.model

/**
 * Created by dimitrioscharoulis on 12/10/15.
 */
case class Message(date: String, from: FromInfo, id: String, mentions: List[Any], message: String, `type`: String){}

object Message {}