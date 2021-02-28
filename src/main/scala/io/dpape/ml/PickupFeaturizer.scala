package io.dpape.ml

import io.dpape.domain.{FeaturizedPickup, Pickup}
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.{Dataset, SparkSession}

class PickupFeaturizer {

  private val vectorAssembler =
    new VectorAssembler()
      .setInputCols(Array("latitude", "longitude"))
      .setOutputCol("features")

  def featurize(pickups: Dataset[Pickup])(implicit sparkSession: SparkSession): Dataset[FeaturizedPickup] = {
    import sparkSession.implicits._

    vectorAssembler.transform(pickups.toDF()).as[FeaturizedPickup]
  }

  def featurize(pickup: Pickup)(implicit sparkSession: SparkSession): Dataset[FeaturizedPickup] = {
    import sparkSession.implicits._

    val x: Dataset[Pickup] = sparkSession.createDataset(Seq[Pickup](pickup)).as[Pickup]

    vectorAssembler.transform(x).as[FeaturizedPickup]
  }
}
