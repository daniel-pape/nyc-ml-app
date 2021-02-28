package io.dpape.ml

case class KMeansModelConfig(numberOfClusters: Int,
                             maxIter: Int,
                             tolerance: Double)