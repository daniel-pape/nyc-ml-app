# Overview

This project implements services for a
Machine Learning application for clustering
Uber taxi pickup locations.

Some of the resulting data are made available
for visualisation using Kibana.

## The data set

As data set the Uber pickups data are used.
The data is in CSV format and for demonstration purposes
only a small extract, located in `data/uber.csv`, is used.

The data contains the following information
on Uber pickups in New York City:

* Timestamp: Gives date and time of the Uber pickup
* Latitude: The latitude of the Uber pickup
* Longitude: The longitude of the Uber pickup

## Features

### What is implemented?

The following features are currently implemented:
* Train a simple K-means clustering model on a training set and
save it for later usage.
* Write the resulting cluster centers as geo-spatial data
to Elasticsearch.
* Write the pickups as geo-spatial data
to Elasticsearch including their timestamp.
* Kibana can be used to visualize these data on a map.
* Make the clustering model available for online prediction:
we run a Kafka io.dpape.dev.consumer, which processes events from topic named `request`.
Such an event is represented by a file path, specifying a JSON file containing
pickups.

**Note:** In a more advanced implementation a request event would wrap besides
such a data location also the information on where to store the resulting predictions and
would indicate how the requester wants to be notified (e.g. via email).
This additional information would then be used to emit a corresponding new event
to Kafka and should be picked up by another service, which does the notification.)
On each such event, the io.dpape.dev.consumer uses a already running streaming Spark job to make predictions
on the data specified by the event. For simplicity and illustration purposes, the resulting
data frame is only printed to the console.

### What is NOT implemented?

* We currently do not search (on a grid) for optimal
parameters of the K-means model based on a suitable
metric. We just provide a means to train such a model
and make it available for predictions. However, we could
extend the application to process further events from the event
backbone which would trigger the training of such models in batch mode
and then use them and could also collect and provide information on their
explanatory power.
* A REST API for submitting events.

# How to use

## Prerequisites

You have the following installed:
* Git
* Docker Compose 1.27.4

## Downloading

Clone the repository to the `PROJECT_DIR` you want to use.

```bash
git clone ... $PROJECT_DIR
```

## Running

### Starting the required services

Open a terminal and start the services used by the application:

```bash
cd $PROJECT_DIR
docker-compose up
```

You can open http://localhost:5601/ in your browser to access Kibana and
http://localhost:8080/ to access Elasticvue.

### Building the model

To train the initial model used by the other applications execute the
following application:

```bash
$PROJECT_DIR/src/main/scala/io/dpape/apps/TrainClusteringModelApp.scala
```

### Setting up the Elasticsearch indices

Execute the shell script `create_indices.sh`
to create the indices for the pickup locations and cluster centers:

```bash
sh $PROJECT_DIR/scripts/create_indices.sh
```

### Populate the Elasticsearch indices

Execute the following Spark applications:

* `$PROJECT_DIR/src/main/scala/io.dpape.apps/PopulatePickupsEsIndexApp.scala`
* `$PROJECT_DIR/src/main/scala/io.dpape.apps/PopulateClusterCentersEsIndexApp.scala`

**Warning:** Be sure to setup the indices properly
by using `create_indices.sh`. See the trouble shooting
section below.

### Creating the Kafka topics

```bash
docker-compose up
docker exec -it dpa-kafka bash
cd opt/kafka_2.13-2.6.0/

# Create the topic for pending request:
./bin/kafka-topics.sh --zookeeper zookeeper:2181 --create --topic requests --partitions 2 --replication-factor 1
```

### Running the prediction service

The prediction services consumes events from
Kafka and performs predictions on the pickup data
referred by these events:

* Start `$PROJECT_DIR/src/main/scala/io.dpape.apps/StreamingPredictionApp.scala`
* Run `$PROJECT_DIR/src/main/scala/io.dpape.dev.consumer/RequestProducer.scala`
to push a single prediction request to Kafka

## Stopping and clean-up

To stop the running containers, open a Terminal and run
the following command:

```bash
cd $PROJECT_DIR
docker-compose down
```

Finally, `rm -rf /tmp/model` removes the stored model.

# Trouble shooting

* It is best to create the Elasticsearch indices by using the script `create_indices.sh`
and the PUT statements it contains. Otherwise, Spark auto-creates the indices and the
the desired geo-spatial fields might get the wrong type. This should
also be done if you delete the indices and repopulate them. Kibana will
complain if you reload the data in an already existing map if the indices are
not recreated properly.

* Avoid reformatting `data/json/pickups.json` (e.g. with an IDE): Spark
expects the data in a single line.

# Versioning and interoperability

* Elasticsearch does not offer Scala 2.12 support for Spark as of 28.02.2021.
Likewise there is no support for Spark 3.x.x.
* kafka-json-schema-serializer-5.4.0.jar seems unavailable from https://packages.confluent.io/maven/
or other sources. According to https://docs.confluent.io/platform/current/installation/versions-interoperability.html
this would be the version required to use the Json serialization offered by
Kafka and Confluences Schema registry, respectively.


