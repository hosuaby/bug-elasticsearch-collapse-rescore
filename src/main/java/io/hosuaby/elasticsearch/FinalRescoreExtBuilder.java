package io.hosuaby.elasticsearch;

import java.io.IOException;
import java.util.Objects;

import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.search.SearchExtBuilder;
import org.elasticsearch.search.rescore.QueryRescorerBuilder;
import org.elasticsearch.search.rescore.RescorerBuilder;

public class FinalRescoreExtBuilder extends SearchExtBuilder {
    public static final String FINAL_RESCORE_EXT = "final-rescore";

    private final QueryRescorerBuilder rescorer;

    public FinalRescoreExtBuilder(StreamInput in) {
        try {
            this.rescorer = new QueryRescorerBuilder(in);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public FinalRescoreExtBuilder(QueryRescorerBuilder rescorer) {
        this.rescorer = rescorer;
    }

    public QueryRescorerBuilder getRescorer() {
        return rescorer;
    }

    public static FinalRescoreExtBuilder parseFromXContent(XContentParser parser) throws IOException {
        QueryRescorerBuilder rescorer = (QueryRescorerBuilder) RescorerBuilder.parseFromXContent(parser);
        return new FinalRescoreExtBuilder(rescorer);
    }

    @Override
    public String getWriteableName() {
        return FINAL_RESCORE_EXT;
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        out.writeNamedWriteable(this);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(FINAL_RESCORE_EXT);
        rescorer.doXContent(builder, params);
        return builder.endObject();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        FinalRescoreExtBuilder another = (FinalRescoreExtBuilder) other;
        return Objects.equals(rescorer, another.rescorer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rescorer);
    }
}
