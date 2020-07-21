#!/usr/bin/env bash

echo "Remove old _ltr index..."
curl -s -XDELETE "localhost:9200/_ltr" | jq

echo "Create _ltr index..."
curl -X PUT "localhost:9200/_ltr" | jq

echo "Create simple ltr feature set..."
curl -X POST "localhost:9200/_ltr/_featureset/test-model" \
-H "Content-Type: application/json" \
-d '
{
    "featureset": {
        "features": [
            {
                "name": "name_query",
                "params": ["keywords"],
                "template_language": "mustache",
                "template": {
                "match": { "name": "{{keywords}}" }
            }
        }]
    }
}' | jq

echo "Create simple ltr model..."
curl -X POST "localhost:9200/_ltr/_featureset/test-model/_createmodel" \
-H "Content-Type: application/json" \
-d '
{
    "model": {
        "name": "linear_model",
        "model": {
            "type": "model/linear",
            "definition": { "name_query" : 1.2 }
        }
    }
}' | jq
