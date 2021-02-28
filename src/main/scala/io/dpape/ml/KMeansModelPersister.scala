package io.dpape.ml

import io.dpape.config.Files
import org.apache.spark.ml.clustering.KMeansModel

class KMeansModelPersister() {
  def persist(model: KMeansModel, modelIdentifier: String): Unit = {
    model.write.overwrite().save(Files.modelSavePath)
  }
}
