package io.dpape.apps

import io.dpape.domain.{FeaturizedPickup, Pickup}
import io.dpape.ml.{KMeansModelConfig, KMeansModelPersister, KMeansModelTrainingJob, PickupFeaturizer}
import io.dpape.reader.PickupsReader
import org.apache.spark.ml.clustering.KMeansModel
import org.apache.spark.sql.{Dataset, SparkSession}

object TrainClusteringModelApp {
  def main(args: Array[String]): Unit = {
    implicit val sparkSession: SparkSession = getOrCreateSparkSession(this)

    val pickups: Dataset[Pickup] = new PickupsReader().read()

    val featurizer = new PickupFeaturizer()

    import sparkSession.implicits._

    val df: Dataset[FeaturizedPickup] = featurizer.featurize(pickups).as[FeaturizedPickup]
    val trainingData = df

    val model: KMeansModel = new KMeansModelTrainingJob(
      KMeansModelConfig(
        10,
        10,
        0.01
      )
    ).train(trainingData)

    val tsCreated = new java.sql.Timestamp(System.currentTimeMillis())
    val modelIdentifier = s"${tsCreated}-${model.uid}"

    new KMeansModelPersister().persist(model, modelIdentifier)
  }
}
