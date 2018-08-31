package abtechsoft

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{StreamsBuilder, StreamsConfig}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object TestStream extends App {
  def start(): Future[Unit] = Future {

    val builder = new StreamsBuilder()
    val config = {
      val settings = new Properties
      settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-function-scala-example")
      settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "10.0.0.72:9092")
      settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
      settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
      settings.put("auto.offset.reset", "earliest")
      settings
    }
    new StreamsConfig(config)
  }

  start()
}
