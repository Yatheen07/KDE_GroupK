package com.groupk.GraphDBIntegration;

import org.apache.solr.common.util.Hash;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.eclipse.rdf4j.query.Binding;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.model.Value;
import com.groupk.GraphDBIntegration.QueryUtil;
import org.openrdf.query.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QueryHandler {

    public ArrayList<HashMap<String,String>> executeQuery(String queryID, Map<String,String> params) throws Exception {
        ArrayList<HashMap<String,String>> result = new ArrayList<>();
        HTTPRepository repository = new HTTPRepository("http://Yatheens-MacBook-Pro.local:7200/repositories/test");
        RepositoryConnection connection = (RepositoryConnection) repository.getConnection();
        try {
            String query = resolveQuery(queryID);
            // Preparing a SELECT query for later evaluation
            TupleQueryResult tupleQueryResult = QueryUtil.evaluateSelectQuery(connection,query);
            HashMap<String,String> temp = new HashMap<>();
            while (tupleQueryResult.hasNext()) {
                // Each result is represented by a BindingSet, which corresponds to a result row
                BindingSet bindingSet = tupleQueryResult.next();
                // Each BindingSet contains one or more Bindings
                temp = new HashMap<>();
                for (Binding binding : bindingSet) {
                    // Each Binding contains the variable name and the value for this result row
                    String name = binding.getName();
                    Value value = binding.getValue();

                    System.out.println(name + " = " + value);
                    String ans = value.toString().contains("^^") ? value.toString().split("\\^\\^")[0] : value.toString();
                    temp.put(name,ans);
                }
                result.add(temp);
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
        return result;
    }

    private String resolveQuery(String query){
        return switch (query) {
            case "query1" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName ?ghg ?gdp
                    WHERE {
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                                 oxly:hasEconomy ?eco .
                        ?sus oxly:countryPollution ?pollution .
                        ?pollution oxly:ghgEmission ?ghg .
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:gdpValue ?gdp .
                    }
                    ORDER BY DESC(?ghg)
                    LIMIT 1""";

            case "query2" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName ?hdi ?gdp
                    WHERE {
                       
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                        oxly:hasEconomy ?eco .
                        ?sus oxly:countryHDI ?humanDev .
                        ?humanDev oxly:hdiScore ?hdi .
                       
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:gdpValue ?gdp .
                    }
                    ORDER BY ASC(?gdp)
                    LIMIT 1""";
            case "query3" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName ?ghg ?military
                    WHERE {
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                        oxly:hasEconomy ?eco .
                        ?sus oxly:countryPollution ?pollution .
                        ?pollution oxly:ghgEmission ?ghg .
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:militaryExpenditurePerGdp ?military .
                    }
                    ORDER BY DESC(?ghg)
                    LIMIT 1
                    """;
            case "query4" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName ?ghg ?military
                    WHERE {
                       
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                        oxly:hasEconomy ?eco .
                        ?sus oxly:countryPollution ?pollution .
                        ?pollution oxly:ghgEmission ?ghg .
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:militaryExpenditurePerGdp ?military .
                    }
                    ORDER BY DESC(?ghg)
                    LIMIT 1""";
            case "query5" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT DISTINCT ?countryName ?contiName ?countryName ?population ?airDeathRate
                    WHERE {
                        ?conti oxly:consistsOf ?country ;
                               oxly:continentName ?contiName .
                        ?country oxly:countryName ?countryName ;
                                 oxly:hasSustainability ?sus ;
                                 oxly:hasGeography ?geo .
                        ?sus oxly:countryPollution ?pollution .
                        ?airDeath oxly:deathCount ?airDeathRate ;
                                  oxly:year ?airYear .
                        ?geo oxly:population ?population .

                        FILTER(CONTAINS(str(?airYear), "2017"))
                    }
                    ORDER BY DESC(?airDeathRate)
                    LIMIT 1""";
            case "query6" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName  (AVG(?corr) AS ?corr ) (AVG(?gdp) AS ?gdp) (AVG(?happiness_score) AS ?happiness_score)
                    WHERE {
                       
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                                 oxly:hasEconomy ?eco .
                       
                        ?sus oxly:countryWBI ?well .
                        ?well oxly:corruptionScore ?corr ;
                              oxly:happinessScore ?happiness_score.
                       
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:gdpValue ?gdp .

                    }
                    GROUP BY ?countryName""";
            case "query7" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT ?countryName ?happiness_score ?military
                    WHERE {
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus ;
                                 oxly:hasEconomy ?eco .
                        ?sus oxly:countryWBI ?well .
                       
                        ?well oxly:corruptionScore ?corr ;
                              oxly:happinessScore ?happiness_score.
                       
                        ?eco oxly:countryGDP ?gdp_entity .
                        ?gdp_entity oxly:militaryExpenditurePerGdp ?military .
                    }
                    ORDER BY DESC(?military)
                    LIMIT 1""";
            case "query8" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT DISTINCT ?contiName (AVG(?happiness_score) AS ?happiness_score )
                    WHERE {
                        ?conti oxly:consistsOf ?country ;
                               oxly:continentName ?contiName .
                       
                        ?country oxly:countryName ?countryName ;
                       oxly:hasSustainability ?sus .
                       
                        ?well oxly:happinessScore ?happiness_score.
                       
                        FILTER(CONTAINS(str(?contiName), "Europe"))
                    }
                    GROUP BY ?contiName""";
            case "query9" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT DISTINCT ?countryName ?hdi
                    WHERE {
                        ?conti oxly:consistsOf ?country ;
                               oxly:continentName ?contiName .
                       
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus .
                       
                        ?sus oxly:countryHDI ?humanDev .
                        ?humanDev oxly:hdiScore ?hdi .
                       
                        FILTER(CONTAINS(str(?contiName), "Asia"))
                    }
                    ORDER BY DESC(?hdi)
                    LIMIT 1""";
            case "query10" -> """
                    prefix rr: <http://www.w3.org/ns/r2rml#>
                    prefix geo: <http://www.opengis.net/ont/geosparql#>
                    prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>
                    prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>
                    prefix xsd: <http://www.w3.org/2001/XMLSchema#>
                    prefix oxly: <http://www.example.org/ont/groupK#>

                    SELECT DISTINCT ?contiName (AVG(?hdi) AS ?hdi)
                    WHERE {
                        ?conti oxly:consistsOf ?country ;
                               oxly:continentName ?contiName .
                       
                        ?country oxly:countryName ?countryName ;
                        oxly:hasSustainability ?sus .

                        ?sus oxly:countryHDI ?humanDev .
                        ?humanDev oxly:hdiScore ?hdi .
                    }
                    GROUP BY ?contiName
                    ORDER BY DESC(?hdi)
                    LIMIT 1""";
            default -> "SELECT * WHERE {?s ?p ?o }";
        };
    }
}
//TODO:
