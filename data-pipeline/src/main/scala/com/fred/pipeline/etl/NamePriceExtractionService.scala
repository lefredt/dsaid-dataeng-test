package com.fred.pipeline.etl

import java.nio.file.{Path, Paths}

import akka.stream.Materializer
import akka.stream.alpakka.csv.scaladsl.CsvParsing
import akka.stream.scaladsl.FileIO
import akka.util.ByteString
import cats.syntax.applicative._
import cats.syntax.apply._
import cats.syntax.functor._
import cats.syntax.option._
import cats.syntax.show._
import com.fred.pipeline.util.{DoubleStringUtils, NameUtils}
import logstage.LogIO
import monix.eval.Task
import monix.execution.Scheduler

import scala.concurrent.Future

trait NamePriceExtractionService {
  def process: Future[Unit]
}

class NamePriceExtractionServiceImpl(logger: LogIO[Task])(implicit mat: Materializer, sc: Scheduler)
  extends NamePriceExtractionService {

  // Can be modified to the actual path
  private val InputFilePath: Path = Paths.get("dataset.csv")
  private val OutputFilePath: Path = Paths.get("/data/dataset_clean.csv")

  override def process: Future[Unit] =
    FileIO
      .fromPath(InputFilePath)
      .via(CsvParsing.lineScanner())
      .map(_.map(_.utf8String.trim))
      .drop(1) // Remove header row
      .collect {
        case name :: price :: Nil =>
          (
            NameUtils.extractName(name),
            DoubleStringUtils.stripZeroPrefix(price)
          ).mapN((name, price) => s"${name.show},$price\n")
      }
      .mapAsync(4) {
        case Left(parseError) => logger.warn(s"Failed with ${parseError.getMessage}").as(none).runToFuture
        case Right(row) => Task.now(ByteString(row).some).runToFuture
      }
      .collect { case Some(rowByteString) => rowByteString }
      .runWith(FileIO.toPath(OutputFilePath))
      .flatMap { ioResult =>
        logger.warn(s"Error writing to csv ${ioResult.getError}").whenA(!ioResult.wasSuccessful).runToFuture
      }

}
