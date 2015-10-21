package com.lunatech

import java.net.URL

import org.json4s._

/**
 * Created by olivertupran on 21/10/15.
 */
package object lunabot {


  /**
   * The results of all the processing done before completing the repl post request
   */
  type PreparedResponse = Either[String, (URL, JValue)]


}
