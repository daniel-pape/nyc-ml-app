package io.dpape.reader

import io.dpape.config.Files
import io.dpape.domain.Pickup
import io.dpape.schemas.Schemas.uberCsvSchema
import org.apache.spark.sql.{Dataset, SparkSession}

class PickupsReader(implicit sparkSession: SparkSession) {
  def read(): Dataset[Pickup] = {
    import sparkSession.implicits._

    sparkSession
    .read
    .schema(uberCsvSchema)
    .csv(Files.uberCsvData)
    .as[Pickup]
  }
}