package com.groupk.GraphDBIntegration;

import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.query.Binding;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.model.Value;
import com.groupk.GraphDBIntegration.QueryUtil;
import org.openrdf.query.*;

public class QueryHandler {
    public void hello() throws Exception {

        // Open connection to a new temporary repository
        // (ruleset is irrelevant for this example)
        //RepositoryConnection connection = EmbeddedGraphDB.openConnectionToTemporaryRepository("rdfs");

        /* Alternative: connect to a remote repository

        // Abstract representation of a remote repository accessible over HTTP
        HTTPRepository repository = new HTTPRepository("http://localhost:7200/graphdb/repositories/myrepo");

        // Separate connection to a repository
        RepositoryConnection connection = repository.getConnection();

        */
        HTTPRepository repository = new HTTPRepository("http://Yatheens-MacBook-Pro.local:7200/repositories/kk");
        RepositoryConnection connection = (RepositoryConnection) repository.getConnection();
        try {
            String query = "SELECT * WHERE {?s ?p ?o }";
            // Preparing a SELECT query for later evaluation
            TupleQueryResult tupleQueryResult = QueryUtil.evaluateSelectQuery(connection,query);

            while (tupleQueryResult.hasNext()) {
                // Each result is represented by a BindingSet, which corresponds to a result row
                BindingSet bindingSet = tupleQueryResult.next();

                // Each BindingSet contains one or more Bindings
                for (Binding binding : bindingSet) {
                    // Each Binding contains the variable name and the value for this result row
                    String name = binding.getName();
                    Value value = binding.getValue();

                    System.out.println(name + " = " + value);
                }

                // Bindings can also be accessed explicitly by variable name
                //Binding binding = bindingSet.getBinding("x");
            }

            // Once we are done with a particular result we need to close it
            tupleQueryResult.close();

            // Doing more with the same connection object
            // ...
        } finally {
            // It is best to close the connection in a finally block
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        new QueryHandler().hello();
    }
}
//TODO:
