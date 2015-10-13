package com.lunatech.lunabot.model

/**
 * Model representation of the JSON object sent by HipChat
 */

case class HipChatMessage(event: String, item: Item, webhook_id: String)

case class Item(message: Message, room: Room)

case class Message(date: String, from: FromInfo, id: String, mentions: List[Any], message: String, `type`: String)

case class FromInfo(id: Int, mentionName: String, name: String)

case class Room(id: Int, name: String)
