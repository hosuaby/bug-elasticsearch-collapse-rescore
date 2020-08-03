package io.hosuaby.elasticsearch;

import static io.hosuaby.elasticsearch.FinalRescoreExtBuilder.FINAL_RESCORE_EXT;

import java.util.Collections;
import java.util.List;

import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.plugins.SearchPlugin;
import org.elasticsearch.search.fetch.FetchSubPhase;

public class FinalRescorePlugin extends Plugin implements SearchPlugin {

    @Override
    public List<FetchSubPhase> getFetchSubPhases(FetchPhaseConstructionContext context) {
        return Collections.singletonList(new FinalRescoreFetchSubPhase());
    }

    @Override
    public List<SearchExtSpec<?>> getSearchExts() {
        return Collections.singletonList(
                new SearchExtSpec<>(
                        FINAL_RESCORE_EXT,
                        FinalRescoreExtBuilder::new,
                        FinalRescoreExtBuilder::parseFromXContent));
    }
}
