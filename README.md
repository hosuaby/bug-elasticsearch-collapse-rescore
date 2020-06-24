# bug-elasticsearch-collapse-rescore
Reproduction of bug in Elasticsearch when collapse is used together with rescore

To launch demo:

```bash
$ docker-compose up -d
$ ./reindex-velib.sh
```

Queries can be found in `queries.http`.