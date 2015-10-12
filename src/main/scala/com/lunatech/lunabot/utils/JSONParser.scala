package com.lunatech.lunabot.utils

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json._
/**
 * Created by dimitrioscharoulis on 12/10/15.
 */
class JSONParser(jsonStr: String) {

  val strFormat = "{\n" +
    "    event: 'room_message',\n" +
    "    item: {\n" +
    "        message: {\n" +
    "            date: '2015-01-20T22:45:06.662545+00:00',\n" +
    "            from: {\n" +
    "                id: 1661743,\n" +
    "                mention_name: 'Blinky',\n" +
    "                name: 'Blinky the Three Eyed Fish'\n" +
    "            },\n" +
    "            id: '00a3eb7f-fac5-496a-8d64-a9050c712ca1',\n" +
    "            mentions: [],\n" +
    "            message: '/weather',\n" +
    "            type: 'message'\n" +
    "        },\n" +
    "        room: {\n" +
    "            id: 1147567,\n" +
    "            name: 'The Weather Channel'\n" +
    "        }\n" +
    "    },\n" +
    "    webhook_id: 578829\n" +
    "}"

}
