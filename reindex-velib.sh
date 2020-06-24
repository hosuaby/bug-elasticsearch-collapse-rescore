#!/usr/bin/env bash

# Get directory of this script
realpath() {
    [[ $1 = /* ]] && echo "$1" || echo "$PWD/${1#./}"
}

SCRIPT=`realpath "$0"`
BASEDIR=`dirname $SCRIPT`

echo "Remove old velib index..."
curl -s -XDELETE http://localhost:9200/velib | jq

echo "Recreate velib index..."
curl -s -XPUT -H "Content-Type: application/json" http://localhost:9200/velib -d @data/velib.index.json | jq

echo "Index data..."
cat ${BASEDIR}/data/velib-disponibilite-en-temps-reel.json \
| jq -rc -f ${BASEDIR}/data2bulk.jq \
| curl http://localhost:9200/velib/data/_bulk -H 'Content-Type: application/x-ndjson' --data-binary @- > /dev/null

echo "Done!"