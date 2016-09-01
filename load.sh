#!/bin/bash
url="${1}"
port="${2}"
curl --retry 10 --retry-delay 5 -H "Content-Type: application/json" -X POST -d @./data/provinces.json -v http://${url}:${port}/loadProvinces
curl --retry 10 --retry-delay 5 -H "Content-Type: application/json" -X POST -d @./data/properties.json -v http://${url}:${port}/loadProperties
