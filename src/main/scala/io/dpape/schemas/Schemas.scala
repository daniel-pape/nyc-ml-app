package io.dpape.schemas

import org.apache.spark.sql.types._

object Schemas {
  val uberCsvSchema = StructType(
    Seq(
      StructField("timestamp", TimestampType),
      StructField("latitude", DoubleType),
      StructField("longitude", DoubleType),
      StructField("base", StringType)
    )
  )
}
