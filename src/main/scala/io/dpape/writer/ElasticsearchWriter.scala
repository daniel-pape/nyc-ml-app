package io.dpape.writer

import io.dpape.config.EsResources
import io.dpape.domain.{Location, Pickup}
import org.apache.spark.sql.{Dataset, SparkSession}
import org.elasticsearch.spark.sql._

object ElasticsearchWriter {
  def writeClusterCenters(location: Seq[Location])(implicit sparkSession: SparkSession): Unit = {
    import sparkSession.implicits._

    location
    .map(_.toGeoString)
    .toDF("location")
    .saveToEs(EsResources.clusterCenters)
  }

  def writePickups(pickups: Dataset[Pickup])(implicit sparkSession: SparkSession): Unit = {
    import sparkSession.implicits._

    pickups.map(pickup => (pickup.timestamp, Location(pickup.latitude, pickup.longitude).toGeoString))
    .toDF("timestamp", "location")
    .saveToEs(EsResources.pickups)
  }

}
