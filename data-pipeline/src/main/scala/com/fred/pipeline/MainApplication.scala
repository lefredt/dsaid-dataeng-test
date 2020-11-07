package com.fred.pipeline

import akka.actor.ActorSystem
import akka.stream.{Materializer, SystemMaterializer}
import cats.effect.ExitCode
import cats.syntax.functor._
import com.fred.pipeline.etl.{NamePriceExtractionService, NamePriceExtractionServiceImpl}
import com.fred.pipeline.util.Logging
import com.softwaremill.macwire._
import logstage.LogIO
import monix.eval.{Task, TaskApp}
import monix.execution.Scheduler

import scala.concurrent.ExecutionContext

object MainApplication extends TaskApp {

  implicit val system: ActorSystem = ActorSystem("first-task")
  implicit val ec: ExecutionContext = system.getDispatcher
  implicit val sc: Scheduler = scheduler
  implicit val materializer: Materializer = SystemMaterializer(system).materializer

  private val logIO: LogIO[Task] = LogIO.fromLogger[Task](Logging.getLogger())
  private val extractionService: NamePriceExtractionService = wire[NamePriceExtractionServiceImpl]

  override def run(args: List[String]): Task[ExitCode] = {
    Task
      .deferFuture(extractionService.process)
      .doOnFinish(_ => Task.deferFuture(system.terminate()).void)
      .as(ExitCode.Success)
  }
}
