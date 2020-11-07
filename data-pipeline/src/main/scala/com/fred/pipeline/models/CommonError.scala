package com.fred.pipeline.models

object CommonError {

  case class ParseError(rawInput: Any, additionalMessage: String) extends Exception(s"$additionalMessage: $rawInput")
}
