package io.dpape

import org.apache.spark.sql.SparkSession

package object apps {
  def getOrCreateSparkSession(forApp: Object): SparkSession = {
    SparkSession
    .builder
    .master("local[*]")
    .appName(forApp.getClass.getName)
    .config("sparkSession.es.nodes", "localhost")
    .config("sparkSession.es.port", "9200")
    .config("es.nodes.wan.only", "true")
    .getOrCreate()
  }
}
