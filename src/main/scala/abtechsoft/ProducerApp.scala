package abtechsoft

object ProducerApp extends App {

  import java.util.Properties

  import org.apache.kafka.clients.producer._

  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.ACKS_CONFIG, "all")
  props.put(ProducerConfig.RETRIES_CONFIG, 3: java.lang.Integer)
  props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1: java.lang.Integer)
  props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true: java.lang.Boolean)

  val producer = new KafkaProducer[String, String](props)

  val TOPIC = "streams-plaintext-input1"

  for (i <- 1 to 50) {
    val record = new ProducerRecord[String, String](TOPIC, i.toString, s"hello $i")
    producer.send(record)
  }

  val record = new ProducerRecord[String, String](TOPIC, "last", "the end " + new java.util.Date)
  producer.send(record)

  producer.close()
}
