ThisBuild / scalaVersion := "2.12.14"
ThisBuild / organization := "io.dpape"
ThisBuild / useLog4J := true

val sparkVersion = "3.3.0"
val scalaMajorVersion = "2.12"
val scalaTestVersion = "3.2.15"
val kafkaVersion = "2.4.0"

lazy val root = (project in file("."))
  .settings(
    name := "nyc-ml-app",
  )

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

libraryDependencies ++= Seq(
  // spark
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,
  // scala test
  "org.scalatest" %% "scalatest" % scalaTestVersion % "test",
  "org.scalatest" %% "scalatest-funsuite" % scalaTestVersion % "test",
  // streaming-kafka
  "org.apache.spark" % s"spark-sql-kafka-0-10_${scalaMajorVersion}" % sparkVersion,
  "org.apache.spark" %% "spark-streaming-kafka-0-10" % sparkVersion,

  // kafka
  "org.apache.kafka" %% "kafka" % kafkaVersion,
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,

  // elasticsearch
  "org.elasticsearch" %% "elasticsearch-spark-30" % "8.9.0"
)
