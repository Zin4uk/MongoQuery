package com.zin4uk.query;

import com.zin4uk.query.match.QueryLinkMatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static com.intellij.testFramework.UsefulTestCase.assertEmpty;
import static com.intellij.testFramework.UsefulTestCase.assertNotEmpty;

public class MongoLinkFilterTest {

    MongoLinkFilter mongoLinkFilter = new MongoLinkFilter();

    @Test
    public void detectQueriesFound() {
        final String query = "DEBUG MongoTemplate - Executing aggregation: [ { \"$match\" : { \"id\" : { \"$in\" : [ 1 , 2]}}} ] in collection test";
        final String expected = "db.test.aggregate([ { \"$match\" : { \"id\" : { \"$in\" : [ 1 , 2]}}} ])";
        final String expectedFormatted = "db.test.aggregate(\n" +
            "[\n" +
            "  {\n" +
            "    \"$match\": {\n" +
            "      \"id\": {\n" +
            "        \"$in\": [\n" +
            "          1.0,\n" +
            "          2.0\n" +
            "        ]\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "]\n" +
            ")";

        final List<QueryLinkMatch> queryLinkMatches = mongoLinkFilter.detectQueries(query);
        assertNotEmpty(queryLinkMatches);
        final QueryLinkMatch actual = queryLinkMatches.get(0);
        Assert.assertEquals(expected, actual.getQuery());
        Assert.assertEquals(expectedFormatted, actual.getFormattedQuery());
    }

    @Test
    public void detectQueriesNotFound() {
        final String query = "DEBUG MongoTemplate";
        final List<QueryLinkMatch> queryLinkMatches = mongoLinkFilter.detectQueries(query);
        assertEmpty(queryLinkMatches);
    }

    @Test
    public void detectQueriesBadQuery() {
        final String query = "DEBUG MongoTemplate - Executing aggregation: [ { bad query } ] in collection test";
        final String expected = "db.test.aggregate([ { bad query } ])";
        final String expectedFormatted = "com.google.gson.stream.MalformedJsonException: Expected ':' at line 1 column 10 path $[0].bad";
        final List<QueryLinkMatch> queryLinkMatches = mongoLinkFilter.detectQueries(query);
        assertNotEmpty(queryLinkMatches);
        final QueryLinkMatch actual = queryLinkMatches.get(0);
        Assert.assertEquals(expected, actual.getQuery());
        Assert.assertEquals(expectedFormatted, actual.getFormattedQuery());
    }
}