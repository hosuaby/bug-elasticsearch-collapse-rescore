package io.hosuaby.elasticsearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.TopDocs;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.FetchSubPhase;
import org.elasticsearch.search.internal.SearchContext;
import org.elasticsearch.search.rescore.QueryRescorer;

public class PostRescoreFetchSubPhase implements FetchSubPhase {

    @Override
    public void hitsExecute(SearchContext context, SearchHit[] hits) throws IOException {
        PostRescoreExtBuilder postRescore = (PostRescoreExtBuilder) context.getSearchExt("post-rescore");
        if (postRescore == null) {
            return;
        }

        TopDocs topDocs = context.queryResult().topDocs();

        Map<Integer, SearchHit> hitMap = new HashMap<>();

        for (int i = 0; i < hits.length; i++) {
            int doc = topDocs.scoreDocs[i].doc;
            hitMap.put(doc, hits[i]);
        }

        topDocs = QueryRescorer.INSTANCE.rescore(
                topDocs,
                context.searcher(),
                postRescore.getRescorer().buildContext(context.getQueryShardContext()));
        context.queryResult().topDocs(topDocs, context.queryResult().sortValueFormats());

        for (int i = 0; i < hits.length; i++) {
            int doc = topDocs.scoreDocs[i].doc;
            hits[i] = hitMap.get(doc);
        }
    }
}
