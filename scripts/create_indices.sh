#!/bin/sh

curl -X PUT 'http://localhost:9200/pickup-idx'

curl -X PUT "localhost:9200/pickup-idx/_mapping/pickups?include_type_name=true&pretty" -H 'Content-Type: application/json' -d'
{
  "pickups" : {
    "properties" : {
      "timestamp" : {"type": "date"},
      "location": {"type": "geo_point"}
    }
  }
}
'

curl -X PUT 'http://localhost:9200/cluster-center-idx'

curl -X PUT "localhost:9200/cluster-center-idx/_mapping/cluster-centers?include_type_name=true&pretty" -H 'Content-Type: application/json' -d'
{
  "cluster-centers" : {
    "properties" : {
      "location": {"type": "geo_point"}
    }
  }
}
'