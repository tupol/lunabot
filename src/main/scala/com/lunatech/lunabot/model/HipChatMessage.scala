package com.lunatech.lunabot.model

/**
 * This class is the model representation of the JSON object sent by hipchat
 */

case class HipChatMessage(event: String, item: Item, webhook_id: String)
