package io.dpape.ml

import io.dpape.domain.FeaturizedPickup
import org.apache.spark.ml.clustering.{KMeans, KMeansModel}
import org.apache.spark.sql.Dataset

class KMeansModelTrainingJob(config: KMeansModelConfig) {
  def train(trainingData: Dataset[FeaturizedPickup]): KMeansModel = {
    val kmeans =
      new KMeans()
        .setK(config.numberOfClusters)
        .setMaxIter(config.maxIter)
        .setInitMode("random")
        .setSeed(1234)
        .setTol(config.tolerance)
        .setDistanceMeasure("euclidean")
        .setFeaturesCol("features")
        .setPredictionCol("cluster_id_prediction")

    kmeans.fit(trainingData)
  }
}
