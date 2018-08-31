package abtechsoft

import java.time.Duration
import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRebalanceListener, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

object ConsumerApp extends App {

  val TOPIC = "streams-wordcount-output"

  val props = new Properties()
  props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "something00")
  props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
  props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false: java.lang.Boolean)

  val consumer = new KafkaConsumer[String, java.lang.Long](props)

  consumer.subscribe(util.Collections.singletonList(TOPIC))
  while (true) {
    val records = consumer.poll(Duration.ofMillis(100))
    for (record <- records.asScala)
      println(record.value())
  }
}

class RebalanceListner extends ConsumerRebalanceListener {

  override def onPartitionsRevoked(partitions: util.Collection[TopicPartition]): Unit = {

    println("Following Partitions Revoked ....")
    for (partition <- partitions.asScala)
      println(partition.topic(), partition.partition())
  }

  override def onPartitionsAssigned(partitions: util.Collection[TopicPartition]): Unit = {
    println("Following Partitions Assigned ....")
    for (partition <- partitions.asScala)
      println(partition.topic(), partition.partition())
  }
}
