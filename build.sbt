import sbt.Keys.scalaVersion

val `kafka-stream-quickstart` = Project("kafka-stream-quickstart", file("."))
  .settings(
    name := "kafka-stream-quickstart",
    version := "0.1",
    scalaVersion := "2.12.4",
    libraryDependencies ++= Seq(
      "org.apache.kafka" % "kafka-clients" % "1.0.0",
      "org.apache.kafka" % "kafka-streams" % "1.0.0",
      "org.scala-lang.modules" %% "scala-java8-compat" % "0.8.0"
    ))
