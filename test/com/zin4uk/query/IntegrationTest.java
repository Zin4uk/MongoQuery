package com.zin4uk.query;

import com.intellij.execution.ConsoleFolding;
import com.intellij.execution.console.ConsoleFoldingSettings;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IntegrationTest {

    public static void main(final String[] args) {
        System.out.println("DEBUG MongoTemplate - Executing aggregation: [ { \"$match\" : { \"id\" : { \"$in\" : [ 1 , 2]}}} ] in collection test");
        System.out.println("Just a line");
        System.out.println("DEBUG MongoTemplate - Executing aggregation: [ { \"$match\" : { \"id\" : 1 }} ] in collection test");
        System.out.println("DEBUG MongoTemplate - Executing aggregation: [ { \"$match\" : { \"id\" : 2 }} ] in collection test");
        System.out.println("DEBUG MongoTemplate - Executing aggregation: [ { ... } ] in collection test");
    }
}
