package com.lunatech.lunabot

import java.io._
import org.slf4j.LoggerFactory

import scala.io._
import scala.sys.process._
import scala.util.Try

trait REPL {
  def run(code: String): Try[String]
}

/**
 * scala REPL process
 */
object ReplProc extends REPL {
  val logger = LoggerFactory.getLogger(ReplProc.getClass)

  /**
   * Start a scala REPL process, write Scala code string to STDIN of it, and read from STDOUT and STDERR.
   * @param code String, Scala code to be executed.
   * @return String, scala REPL response, STDERR after STDOUT.
   * @example val responseLinesOfString = ReplProc.run(scalaCodeString)
   * @todo Make REPL persistent process, using :reset to clean up before each session.
   * @todo Seperate STDOUT and STDERR to allow different coloring.
   */
  def run(code: String): Try[String] = Try {
    var respOut = ""
    var respErr = ""
    val cmd = Seq("scala") // Uses Seq here to allow possible command options later
    val process = Process(cmd)
    val io = new ProcessIO(
      in => {
        new PrintStream(in).println(code)
        logger.debug(s"Finished sending $code to in stream")
        in.close()
      },
      out => {
        respOut = Source.fromInputStream(out).getLines().toArray.drop(4).dropRight(2).mkString("\n")
        logger.debug(s"Finished writing $respOut to out stream")
        out.close()
      }, // Remove first 4 lines of welcome message, and the last 2 lines of auto-generated ":quit".
      err => {
        respErr = Source.fromInputStream(err).getLines().mkString("\n")
        logger.debug(s"Found errors while running expression $respErr")
        err.close()
      })
    process.run(io).exitValue() // Wait until the process exits, otherwise respOut and respErr won't get output yet.
    respOut + respErr // TODO: seperate STDOUT and STDERR to allow different coloring.
  }
}
