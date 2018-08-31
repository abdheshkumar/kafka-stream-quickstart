package abtechsoft


object ProducerApp extends App {

  import java.util.Properties

  import org.apache.kafka.clients.producer._

  val props = new Properties()
  props.put("bootstrap.servers", "0.0.0.0:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks", "all")
  props.put("retries", 3: java.lang.Integer)
  props.put("max.in.flight.requests.per.connection", 1: java.lang.Integer)
  props.put("enable.idempotence", true: java.lang.Boolean)

  val producer = new KafkaProducer[String, String](props)
println(":::::::::")
  val TOPIC = "test2"

  for (i <- 1 to 50) {
    println(":::::d::::")
    val record = new ProducerRecord[String, String](TOPIC, i.toString, s"hello $i")
    println(s":::::d${i}::::")
   val a = producer.send(record).get()
    println(a)
  }

  val record = new ProducerRecord[String, String](TOPIC, "last", "the end " + new java.util.Date)
  producer.send(record)

  producer.close()
}