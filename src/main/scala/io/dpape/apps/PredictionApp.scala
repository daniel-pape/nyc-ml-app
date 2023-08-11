package io.dpape.apps

import io.dpape.config.{Files, KafkaConfig, KafkaTopics}
import io.dpape.domain.Pickup
import io.dpape.ml.{KMeansModelLoader, PickupFeaturizer}
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, to_timestamp}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}

import java.time.Duration
import java.util
import scala.collection.JavaConverters._

object PredictionApp {
  def main(args: Array[String]): Unit = {
    implicit val sparkSession: SparkSession = getOrCreateSparkSession(this)

    val model = new KMeansModelLoader().load(Files.modelSavePath)
    val featurizer = new PickupFeaturizer()

    val consumer = new KafkaConsumer[String, String](KafkaConfig.getDefaultConsumerProps)
    consumer.subscribe(util.Arrays.asList(KafkaTopics.requestsTopic))

    while (true) {
      val record = consumer.poll(Duration.ofMillis(1000)).asScala
      for (data <- record.iterator) {

        val filePath = data.value()

        import sparkSession.implicits._

        val pickups =
          sparkSession
          .read
          .format("json")
          .schema(
            StructType(
              Seq(
                StructField("timestamp", StringType),
                StructField("latitude", DoubleType),
                StructField("longitude", DoubleType),
                StructField("base", StringType)
              )
            )
          )
          .load(filePath).withColumn("timestamp", to_timestamp(col("timestamp")))
          .as[Pickup].transform(pickupDs => model.transform(featurizer.featurize(pickupDs)))

        pickups.show(truncate = false)
      }
    }
  }
}
