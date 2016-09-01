#!/bin/bash
curl --retry 10 --retry-delay 5 -H "Content-Type: application/json" -X POST -d @./data/provinces.json -v http://localhost:8080/loadProvinces
curl --retry 10 --retry-delay 5 -H "Content-Type: application/json" -X POST -d @./data/properties.json -v http://localhost:8080/loadProperties
