package io.dpape.producer

import io.dpape.config.{Files, KafkaConfig, KafkaTopics}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object RequestProducer {
  def main(args: Array[String]): Unit = {
    val producer = new KafkaProducer[String, String](KafkaConfig.getDefaultProducerProps)
    val record = new ProducerRecord[String, String](KafkaTopics.requestsTopic, "key", Files.pickupsJsonData)

    producer.send(record)
    producer.close()
  }
}
