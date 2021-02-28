package io.dpape.apps

import io.dpape.domain.Pickup
import io.dpape.reader.PickupsReader
import io.dpape.writer.ElasticsearchWriter
import org.apache.spark.sql.{Dataset, SparkSession}

object PopulatePickupsEsIndexApp {
  def main(args: Array[String]): Unit = {
    implicit val spark: SparkSession = getOrCreateSparkSession(this)

    val pickups: Dataset[Pickup] = new PickupsReader().read()

    ElasticsearchWriter.writePickups(pickups)
  }
}
