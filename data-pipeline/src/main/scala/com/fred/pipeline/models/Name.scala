package com.fred.pipeline.models

import cats.Show

case class Name(first: String, last: String)

object Name {
  implicit val showFirstAndLastName: Show[Name] = Show.show[Name](name => s"${name.first} ${name.last}")
}
