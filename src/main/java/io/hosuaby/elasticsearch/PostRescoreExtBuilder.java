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

public class PostRescoreExtBuilder extends SearchExtBuilder {
    private QueryRescorerBuilder rescorer;

    public PostRescoreExtBuilder(StreamInput in) {
        try {
            // TODO: find a way to instanciate any rescorer implementation, not only QueryRescorerBuilder
            this.rescorer = new QueryRescorerBuilder(in);
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    private PostRescoreExtBuilder(QueryRescorerBuilder rescorer) {
        this.rescorer = rescorer;
    }

    public QueryRescorerBuilder getRescorer() {
        return rescorer;
    }

    public static PostRescoreExtBuilder parseFromXContent(XContentParser parser) throws IOException {
        QueryRescorerBuilder rescorer = (QueryRescorerBuilder) RescorerBuilder.parseFromXContent(parser);
        return new PostRescoreExtBuilder(rescorer);
    }

    @Override
    public String getWriteableName() {
        return "post-rescore";
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        rescorer.writeTo(out);
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        return rescorer.toXContent(builder, params);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        PostRescoreExtBuilder another = (PostRescoreExtBuilder) other;
        return Objects.equals(rescorer, another.rescorer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rescorer);
    }
}
