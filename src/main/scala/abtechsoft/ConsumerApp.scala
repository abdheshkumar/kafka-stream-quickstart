package abtechsoft

import java.util
import java.util.Properties

import org.apache.kafka.clients.consumer.{ConsumerRebalanceListener, KafkaConsumer}
import org.apache.kafka.common.TopicPartition

import scala.collection.JavaConverters._

object ConsumerApp extends App {


  val TOPIC = "test1"

  val props = new Properties()
  props.put("bootstrap.servers", "10.0.0.72:9092")
  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("group.id", "something2")
  //props.put("auto.offset.reset", "earliest")

  val consumer = new KafkaConsumer[String, String](props)

  consumer.subscribe(util.Collections.singletonList(TOPIC), new RebalanceListner)
  while (true) {
    val records = consumer.poll(1000000)
    for (record <- records.asScala) {
      println(record)
    }
  }
}


class RebalanceListner extends ConsumerRebalanceListener {

  override def onPartitionsRevoked(partitions: util.Collection[TopicPartition]): Unit = {

    System.out.println("Following Partitions Revoked ....")
    for (partition <- partitions.asScala) {
      println(partition.topic(), partition.partition())
    }
  }

  override def onPartitionsAssigned(partitions: util.Collection[TopicPartition]): Unit = {
    System.out.println("Following Partitions Assigned ....")
    for (partition <- partitions.asScala) {
      println(partition.topic(), partition.partition())
    }
  }
}
