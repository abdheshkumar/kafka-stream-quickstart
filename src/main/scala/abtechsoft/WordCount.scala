package abtechsoft

import java.util.Properties
import java.util.concurrent.TimeUnit

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
//import Implicits._
import scala.collection.JavaConverters._

object WordCount extends App {
  val builder: StreamsBuilder = new StreamsBuilder

  val streamingConfig: Properties = {
    val settings = new Properties
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-function-scala-example")
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings
  }

  val stringSerde = Serdes.String()
  val longSerde = Serdes.Long()
  val textLines = builder.stream("streams-plaintext-input", Consumed.`with`(stringSerde, stringSerde))
  val wordCounts: KTable[String, java.lang.Long] = textLines
    .flatMapValues(value => value.toLowerCase().split("\\W+").toBuffer.asJava)
    .groupBy((_, value) => value)
    .count()
  wordCounts.toStream().to("streams-wordcount-output", Produced.`with`(stringSerde, longSerde))


  val streams = new KafkaStreams(builder.build(), streamingConfig)
  streams.start()
  println("Kafka streaming started...")
  sys.ShutdownHookThread {
    streams.close(10, TimeUnit.SECONDS)
  }
}
