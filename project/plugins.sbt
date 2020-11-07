resolvers += Resolver.bintrayIvyRepo("rallyhealth", "sbt-plugins")
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

addSbtPlugin(dependency = "com.rallyhealth.sbt" % "sbt-git-versioning" % "1.4.0", "1.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.10")
