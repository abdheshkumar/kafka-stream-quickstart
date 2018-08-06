package abtechsoft

import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.{StreamsBuilder, StreamsConfig}
//import Implicits._
import scala.collection.JavaConverters._

object WordCount extends App {
  val bootstrapServers = "localhost:9092"
  val builder = new StreamsBuilder

  val streamingConfig = {
    val settings = new Properties
    settings.put(StreamsConfig.APPLICATION_ID_CONFIG, "map-function-scala-example")
    settings.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    settings.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray.getClass.getName)
    settings.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass.getName)
    settings
  }

  // Serializers/deserializers (serde) for String and Long types
  val stringSerde = Serdes.String()
  val longSerde = Serdes.Long()

  // Construct a `KStream` from the input topic "streams-plaintext-input", where message values
  // represent lines of text (for the sake of this example, we ignore whatever may be stored
  // in the message keys).
  val textLines = builder.stream("streams-plaintext-input", Consumed.`with`(stringSerde, stringSerde))

  val wordCounts: KTable[String, java.lang.Long] = textLines
    // Split each text line, by whitespace, into words.
    .flatMapValues(value => value.toLowerCase().split("\\W+").toBuffer.asJava)
    // Group the text words as message keys
    .groupBy((_, value) => value)
    // Count the occurrences of each word (message key).
    .count()

  // Store the running counts as a changelog stream to the output topic.
  wordCounts.toStream().to("streams-wordcount-output", Produced.`with`(Serdes.String(), Serdes.Long()))
}
