import sbt.Keys.scalaVersion

val workaround = {
  sys.props += "packaging.type" -> "jar"
  ()
}

val `kafka-stream-quickstart` = Project("kafka-stream-quickstart", file("."))
  .settings(
    name := "kafka-stream-quickstart",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "2.0.0",
      "org.apache.kafka" % "kafka-streams" % "2.0.0"
    ))
