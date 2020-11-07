package com.fred.pipeline.util

import izumi.logstage.api.logger.LogRouter
import izumi.logstage.api.routing.StaticLogRouter
import logstage._
import logstage.circe.LogstageCirceRenderingPolicy

object Logging {

  def getLogger(router: LogRouter = defaultLogRouter): IzLogger = {
    // route Slf4j logging from e.g ES
    StaticLogRouter.instance.setup(router)
    IzLogger(router)
  }

  def defaultLogRouter: LogRouter = {
    // use async logger
    val asyncConsoleSink = new QueueingSink(ConsoleSink(LogstageCirceRenderingPolicy(prettyPrint = false)))
    asyncConsoleSink.start()
    ConfigurableLogRouter(Info, asyncConsoleSink)
  }
}
