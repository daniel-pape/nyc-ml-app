package io.dpape

import java.io.File
import java.util.Properties

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig

package object config {
  object Resources {
    def getFile(relativeResourcePath: String): File =
      new File(getClass.getClassLoader.getResource(relativeResourcePath).getPath)
  }

  object Files {
    val pickupsJsonData: String = Resources.getFile("data/json").getAbsolutePath
    val uberCsvData: String = Resources.getFile("data/csv/uber.csv").getAbsolutePath
    val modelSavePath: String = "/tmp/model"
  }

  object EsResources {
    val clusterCenters = "cluster-center-idx/cluster-centers"
    val pickups = "pickup-idx/pickups"
  }

  object KafkaTopics {
    val requestsTopic = "requests"
  }

  object KafkaConfig {
    private val kafkaBootstrapServer = "localhost:9092"

    def getDefaultConsumerProps: Properties = {
      val props = new Properties()
      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer)
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
      props.put(ConsumerConfig.GROUP_ID_CONFIG, "io.dpape.dev.consumer-group")

      props
    }

    def getDefaultProducerProps: Properties = {
      val props = new Properties()
      props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServer)
      props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
      props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")

      props
    }
  }
}
