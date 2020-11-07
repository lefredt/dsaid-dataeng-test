import sbt._

object Dependencies {

  // Track your versions here
  private val akkaV = "2.5.31"
  private val akkaAlpakkaCsvV = "2.0.2"
  private val catsCoreV = "2.2.0"
  private val catsEffectV = "2.2.0"
  private val izumiV = "0.10.14"
  private val macwireV = "2.3.7"
  private val monixV = "3.2.2"

  // Compile-time DI
  val macWireMacros = "com.softwaremill.macwire" %% "macros" % macwireV % "provided"
  val macWireUtil = "com.softwaremill.macwire"   %% "util"   % macwireV

  val akkaActor = "com.typesafe.akka"  %% "akka-actor-typed" % akkaV
  val akkaStream = "com.typesafe.akka" %% "akka-stream"      % akkaV

  val alpakkaCsv = "com.lightbend.akka" %% "akka-stream-alpakka-csv" % akkaAlpakkaCsvV

  val catsCore = "org.typelevel"   %% "cats-core"   % catsCoreV
  val catsEffect = "org.typelevel" %% "cats-effect" % catsEffectV

  val izumiDeps = Seq(
    "io.7mind.izumi" %% "logstage-core"            % izumiV,
    "io.7mind.izumi" %% "logstage-rendering-circe" % izumiV
  )
  val izumiAdaptor = "io.7mind.izumi" %% "logstage-adapter-slf4j" % izumiV

  val monix = "io.monix" %% "monix" % monixV

  lazy val commonDeps = izumiDeps ++ Seq(
    akkaActor,
    akkaStream,
    alpakkaCsv,
    catsCore,
    catsEffect,
    macWireMacros,
    macWireUtil
  )

  lazy val implDeps = commonDeps ++ Seq(
    izumiAdaptor,
    monix
  )
}
