
ThisBuild / organization := "com.fred.dataeng"
ThisBuild / scalaVersion := "2.13.3"
ThisBuild / name := """dsaid-dataeng-test"""
ThisBuild / organizationName := "fredrick"

publish := {}
publishLocal := {}

lazy val home_assignment = (project in file("."))
  .settings(publish / skip := true, crossScalaVersions := Nil)
  .aggregate(pipeline)

lazy val baseSettings = Seq(
  scalacOptions ++= Seq(
    "-Xmacro-settings:materialize-derivations",
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-unused:imports", // Warn if an import selector is not referenced.
    "-deprecation",
    "-language:higherKinds",
    "-encoding",
    "UTF-8"
  ),
  publishArtifact := true,
  publishArtifact in Test := false,
  sources in (Compile, doc) := Seq.empty
)

lazy val pipeline = (project in file("data-pipeline"))
  .settings(baseSettings: _*)
  .settings(
    assemblyMergeStrategy in assembly := {
      case x if Assembly.isConfigFile(x) =>
        MergeStrategy.concat
      case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
      case _                                   => MergeStrategy.first
    },
    assemblyJarName in assembly := {
      s"${name.value}-assembly-${version.value}.jar"
    },
    assemblyOutputPath in assembly := {
      (baseDirectory in assembly).value / ".." / "target/dist" / (assemblyJarName in assembly).value
    },
    test in assembly := {}
  )
  .settings(libraryDependencies ++= Dependencies.implDeps)


lazy val databases = (project in file("database"))
  .settings(baseSettings: _*)