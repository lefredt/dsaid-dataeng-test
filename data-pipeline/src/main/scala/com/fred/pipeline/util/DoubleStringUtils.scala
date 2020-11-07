package com.fred.pipeline.util

import cats.syntax.either._
import com.fred.pipeline.models.CommonError._

object DoubleStringUtils {

  def stripZeroPrefix(input: String): Either[ParseError, Double] = {
    input.toDoubleOption.fold(ifEmpty = ParseError(input, "Number not float").asLeft[Double])(_.asRight[ParseError])
  }
}
