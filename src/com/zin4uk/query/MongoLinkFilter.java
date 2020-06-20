package com.zin4uk.query;

import com.intellij.execution.filters.Filter;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.zin4uk.query.match.QueryLinkMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongoLinkFilter implements Filter {

    private final Matcher queryMatcher;

    private static final String QUERY_BLOCK = "query";
    private static final String COLLECTION_BLOCK = "collection";
    private static final Pattern MONGO_QUERY_PATTERN = Pattern.compile(
        "Executing aggregation: (?<" + QUERY_BLOCK + ">.+) in collection (?<" + COLLECTION_BLOCK + ">.+)$",
        Pattern.UNICODE_CHARACTER_CLASS);

    public MongoLinkFilter() {
        queryMatcher = MONGO_QUERY_PATTERN.matcher("");
    }

    @Nullable
    @Override
    public Result applyFilter(final String line, final int endPoint) {
        final int startPoint = endPoint - line.length();
        return new Result(getResultItemsUrl(line, startPoint));
    }

    @NotNull
    public List<ResultItem> getResultItemsUrl(final String line, final int startPoint) {
        final List<ResultItem> results = new ArrayList<>();
        final List<QueryLinkMatch> matches = detectQueries(line);

        for (final QueryLinkMatch match : matches) {
            results.add(
                new Result(
                    startPoint + match.getStart(),
                    startPoint + match.getEnd(),
                    new QueryHyperlinkInfo(match))

            );
        }
        return results;
    }

    @NotNull
    public List<QueryLinkMatch> detectQueries(@NotNull final String line) {
        queryMatcher.reset(line);
        final List<QueryLinkMatch> results = new LinkedList<>();
        while (queryMatcher.find()) {
            String query = queryMatcher.group(QUERY_BLOCK);
            String collection = queryMatcher.group(COLLECTION_BLOCK);
            results.add(new QueryLinkMatch(query, collection,
                queryMatcher.start(),
                queryMatcher.end()));
        }
        return results;
    }

}
