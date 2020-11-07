package com.fred.pipeline.util

import cats.syntax.either._
import com.fred.pipeline.models.CommonError._
import com.fred.pipeline.models.Name

object NameUtils {
  private val designationList: Set[String] = Set("mr.", "mister", "ms.", "miss", "mrs.", "dr.", "phd", "jr.", "sr.")

  def extractName(raw: String): Either[ParseError, Name] = {
    val name =
      raw
        .split(" ")
        .filterNot(_.forall(_.isUpper))
        .filterNot(namePart => designationList.contains(namePart.toLowerCase))

    name.toList match {
      case first :: last :: Nil => Name(first, last).asRight[ParseError]
      case unmatchName => ParseError(unmatchName, "Missing first or last").asLeft[Name]
    }
  }
}
