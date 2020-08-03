package io.hosuaby.elasticsearch;

import static io.hosuaby.elasticsearch.FinalRescoreExtBuilder.FINAL_RESCORE_EXT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.search.TopDocs;
import org.elasticsearch.index.query.QueryShardContext;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.FetchSubPhase;
import org.elasticsearch.search.internal.SearchContext;
import org.elasticsearch.search.rescore.QueryRescorer;
import org.elasticsearch.search.rescore.RescoreContext;

public class FinalRescoreFetchSubPhase implements FetchSubPhase {

    @Override
    public void hitExecute(SearchContext context, HitContext hitContext) throws IOException {
        // TODO: maybe need to code this
    }

    @Override
    public void hitsExecute(SearchContext context, SearchHit[] hits) throws IOException {
        FinalRescoreExtBuilder finalRescore = (FinalRescoreExtBuilder) context.getSearchExt(FINAL_RESCORE_EXT);
        if (hits.length == 0 || finalRescore == null) {
            return;
        }

        if (context.queryResult().hasConsumedTopDocs()) {
            return;
        }

        TopDocs topDocs = context.queryResult().topDocs();

        Map<Integer, SearchHit> hitMap = new HashMap<>();
        for (int i = 0; i < hits.length; i++) {
            int doc = topDocs.scoreDocs[i].doc;
            hitMap.put(doc, hits[i]);
        }

        RescoreContext rescoreContext = finalRescore
                .getRescorer()
                .buildContext(new QueryShardContext(context.getQueryShardContext()));
        topDocs = QueryRescorer.INSTANCE.rescore(topDocs, context.searcher(), rescoreContext);

        context.queryResult().topDocs(topDocs, context.queryResult().sortValueFormats());

        /* Reorder hits in array according the new ordering */
        for (int i = 0; i < hits.length; i++) {
            int doc = topDocs.scoreDocs[i].doc;
            hits[i] = hitMap.get(doc);
        }
    }
}
