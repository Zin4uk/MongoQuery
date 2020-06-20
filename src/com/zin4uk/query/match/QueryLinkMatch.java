package com.zin4uk.query.match;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class QueryLinkMatch {
    public final String query;
    public final String collection;
    public final int start;
    public final int end;

    public QueryLinkMatch(final String query,
                          final String collection,
                          final int start,
                          final int end) {
        this.query = query;
        this.collection = collection;
        this.start = start;
        this.end = end;
    }

    public String getFormattedQuery() {
        final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        final Object jsonQuery = gson.fromJson(query, Object.class);
        final String formattedQuery = gson.toJson(jsonQuery);
        return "db." + collection + ".aggregate(\n" + formattedQuery + "\n)";
    }

    public String getQuery() {
        return "db." + collection + ".aggregate(" + query + ")";
    }

}