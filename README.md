# bug-elasticsearch-collapse-rescore
Reproduction of bug in Elasticsearch when collapse is used together with rescore

Transforme JSON array to JSON Lines:

```bash
$ cat data/velib-disponibilite-en-temps-reel.json | jq -c '.[]' > data/velib.jsonl
```