package io.dpape.domain

import org.apache.spark.ml.linalg.Vector

case class FeaturizedPickup(timestamp: java.sql.Timestamp,
                            latitude: Double,
                            longitude: Double,
                            base: String,
                            features: Vector)
