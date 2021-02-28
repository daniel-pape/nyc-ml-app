name := "nyc-ml-app"

version := "0.1"

scalaVersion := s"${scalaMajorVersion}.12"

val scalaMajorVersion = "2.11"
val sparkVersion = "2.4.4"
val kafkaVersion = "2.4.0"
val log4jVersion = "2.4.1"

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

  // streaming-kafka
  "org.apache.spark" % s"spark-sql-kafka-0-10_${scalaMajorVersion}" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,

  // logging
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion,

  // kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,

  // elasticsearch
  "org.elasticsearch" %% "elasticsearch-spark-20" % "7.11.1"
)