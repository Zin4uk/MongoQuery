package com.zin4uk.query;

import com.intellij.execution.filters.Filter;
import com.intellij.ide.browsers.OpenUrlHyperlinkInfo;
import com.intellij.openapi.project.Project;
import com.zin4uk.query.match.QueryLinkMatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MongoLinkFilter implements Filter {

    private static final Pattern MONGO_QUERY_PATTERN = Pattern.compile(
        "Executing aggregation: (?<query>.+) in collection (?<collection>.+)$",
        Pattern.UNICODE_CHARACTER_CLASS);

    private final Matcher queryMatcher;

    public MongoLinkFilter(Project project) {
        queryMatcher = MONGO_QUERY_PATTERN.matcher("");
    }

    @Nullable
    @Override
    public Result applyFilter(final String line, final int endPoint) {
        System.out.println("start");
        final int startPoint = endPoint - line.length();
        return new Result(getResultItemsUrl(line, startPoint));
    }

    public List<ResultItem> getResultItemsUrl(final String line, final int startPoint) {
        final List<ResultItem> results = new ArrayList<>();
        final List<QueryLinkMatch> matches = detectURLs(line);

        for (final QueryLinkMatch match : matches) {
            results.add(
                new Result(
                    startPoint + match.start,
                    startPoint + match.end,
                    new QueryHyperlinkInfo(match))
            );
        }
        return results;
    }

    @NotNull
    public List<QueryLinkMatch> detectURLs(@NotNull final String line) {
        queryMatcher.reset(line);
        final List<QueryLinkMatch> results = new LinkedList<>();
        while (queryMatcher.find()) {
            String query = queryMatcher.group("query");
            String collection = queryMatcher.group("collection");
            int startOffset = 0;
            int endOffset = 0;

            results.add(new QueryLinkMatch(query, collection,
                queryMatcher.start() + startOffset,
                queryMatcher.end() - endOffset));
        }
        return results;
    }

}
