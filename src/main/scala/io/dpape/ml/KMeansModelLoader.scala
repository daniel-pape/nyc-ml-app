package io.dpape.ml

import org.apache.spark.ml.clustering.KMeansModel

class KMeansModelLoader {
  def load(from: String): KMeansModel = {
    KMeansModel.load(from)
  }
}
