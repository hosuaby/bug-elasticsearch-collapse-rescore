package io.hosuaby.elasticsearch;

import java.util.Collections;
import java.util.List;

import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;
import org.elasticsearch.search.fetch.FetchSubPhase;

public class PostRescorePlugin extends Plugin implements SearchPlugin {

    @Override
    public List<FetchSubPhase> getFetchSubPhases(FetchPhaseConstructionContext context) {
        return Collections.singletonList(new PostRescoreFetchSubPhase());
    }

    @Override
    public List<SearchExtSpec<?>> getSearchExts() {
        return Collections.singletonList(
                new SearchExtSpec<>(
                        "post-rescore",
                        PostRescoreExtBuilder::new,
                        PostRescoreExtBuilder::parseFromXContent));
    }
}
