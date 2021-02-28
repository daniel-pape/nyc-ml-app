package io.dpape.apps

import io.dpape.config.Files
import io.dpape.domain.Location
import io.dpape.ml.KMeansModelLoader
import io.dpape.writer.ElasticsearchWriter
import org.apache.spark.sql.SparkSession

object PopulateClusterCentersEsIndexApp {
  def main(args: Array[String]): Unit = {
    implicit val sparkSession: SparkSession = getOrCreateSparkSession(this)

    val model = new KMeansModelLoader().load(Files.modelSavePath)

    ElasticsearchWriter.writeClusterCenters(
      model
      .clusterCenters
      .map(vector => Location(vector.apply(0), vector.apply(1)))
    )
  }
}
