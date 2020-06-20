package com.zin4uk.query.match;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class QueryLinkMatch {
    private final String query;
    private final String collection;
    private final int start;
    private final int end;

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
        try {
            final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
            final Object jsonQuery = gson.fromJson(query, Object.class);
            final String formattedQuery = gson.toJson(jsonQuery);
            return "db." + collection + ".aggregate(\n" + formattedQuery + "\n)";
        } catch (JsonSyntaxException ex) {
            return ex.getMessage();
        }
    }

    public String getQuery() {
        return "db." + collection + ".aggregate(" + query + ")";
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}