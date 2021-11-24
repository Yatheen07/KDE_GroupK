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
        HTTPRepository repository = new HTTPRepository("http://DESKTOP-41GLD0T:7200/repositories/test");
        RepositoryConnection connection = (RepositoryConnection) repository.getConnection();
        try {
            System.out.println("params " + params.keySet());
            System.out.println("queryID " +queryID);
            String query = resolveQuery(queryID,params);
            System.out.println(query);
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
                    name = "\"" + name + "\"";
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

    private String resolveQuery(String query, Map<String,String> params){
        return switch (query) {
            case "query1" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT ?countryName ?ghg ?gdp\n" +
                             "WHERE {\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "    oxly:hasSustainability ?sus ;\n" +
                             "             oxly:hasEconomy ?eco .\n" +
                             "    ?sus oxly:countryPollution ?pollution .\n" +
                             "    ?pollution oxly:ghgEmission ?ghg .\n" +
                             "    ?eco oxly:countryGDP ?gdp_entity .\n" +
                             "    ?gdp_entity oxly:gdpValue ?gdp .\n" +
                             "}\n" +
                             "ORDER BY " + params.get("sort") + "(?ghg)\n" +
                             "LIMIT 1";


            case "query2" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT ?countryName ?hdi ?gdp\n" +
                             "WHERE {\n" +
                             "\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "    oxly:hasSustainability ?sus ;\n" +
                             "    oxly:hasEconomy ?eco .\n" +
                             "    ?sus oxly:countryHDI ?humanDev .\n" +
                             "    ?humanDev oxly:hdiScore ?hdi .\n" +
                             "\n" +
                             "    ?eco oxly:countryGDP ?gdp_entity .\n" +
                             "    ?gdp_entity oxly:gdpValue ?gdp .\n" +
                             "}\n" +
                             "ORDER BY " + params.get("sort") + "(?gdp)\n" +
                             "LIMIT 1";
            case "query3" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT ?countryName ?ghg ?military\n" +
                             "WHERE {\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "    oxly:hasSustainability ?sus ;\n" +
                             "    oxly:hasEconomy ?eco .\n" +
                             "    ?sus oxly:countryPollution ?pollution .\n" +
                             "    ?pollution oxly:ghgEmission ?ghg .\n" +
                             "    ?eco oxly:countryGDP ?gdp_entity .\n" +
                             "    ?gdp_entity oxly:militaryExpenditurePerGdp ?military .\n" +
                             "}\n" +
                             "ORDER BY " + params.get("sort") +"(?ghg)\n" +
                             "LIMIT 1\n";
            case "query4" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "       prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "       prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "       prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "       prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "       prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "\n" +
                             "\n" +
                             "       SELECT DISTINCT ?contiName (AVG(?ghg) AS ?ghg ) (AVG(?hdi) AS ?hdi)\n" +
                             "       WHERE {\n" +
                             "       ?conti oxly:consistsOf ?country ;\n" +
                             "       oxly:continentName ?contiName .\n" +
                             "       ?country oxly:countryName ?countryName ;\n" +
                             "       oxly:hasSustainability ?sus.\n" +
                             "       ?sus oxly:countryPollution ?pollution ;\n" +
                             "       oxly:countryHDI ?humanDev .\n" +
                             "       ?humanDev oxly:hdiScore ?hdi .\n" +
                             "\n" +
                             "\n" +
                             "\n" +
                             "       ?pollution oxly:ghgEmission ?ghg .\n" +
                             "\n" +
                             "       FILTER(CONTAINS(str(?contiName), \"" + params.get("continent") +"\"))\n" +
                             "       }\n" +
                             "       GROUP BY ?contiName";
            case "query5" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "\n" +
                             "\n" +
                             "SELECT DISTINCT ?countryName ?countryName ?population ?airDeathRate\n" +
                             "WHERE {\n" +
                             "?conti oxly:consistsOf ?country ;\n" +
                             "oxly:continentName ?contiName .\n" +
                             "?country oxly:countryName ?countryName ;\n" +
                             "oxly:hasSustainability ?sus ;\n" +
                             "oxly:hasGeography ?geo .\n" +
                             "?sus oxly:countryPollution ?pollution .\n" +
                             "?airDeath oxly:deathCount ?airDeathRate ;\n" +
                             "oxly:year ?airYear .\n" +
                             "?geo oxly:population ?population .\n" +
                             "\n" +
                             "\n" +
                             "\n" +
                             "FILTER(CONTAINS(str(?airYear), \"2015\"))\n" +
                             "}\n" +
                             "ORDER BY " + params.get("sort") + "(?airDeathRate)\n" +
                             "LIMIT 1\n";
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
            case "query7" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT ?countryName ?happiness_score ?military\n" +
                             "WHERE {\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "    oxly:hasSustainability ?sus ;\n" +
                             "             oxly:hasEconomy ?eco .\n" +
                             "    ?sus oxly:countryWBI ?well .\n" +
                             "\n" +
                             "    ?well oxly:corruptionScore ?corr ;\n" +
                             "          oxly:happinessScore ?happiness_score.\n" +
                             "\n" +
                             "    ?eco oxly:countryGDP ?gdp_entity .\n" +
                             "    ?gdp_entity oxly:militaryExpenditurePerGdp ?military .\n" +
                             "}\n" +
                             "ORDER BY " + params.get("sort") + "(?military)\n" +
                             "LIMIT 1";
            case "query8" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT DISTINCT ?contiName (AVG(?happiness_score) AS ?happiness_score )\n" +
                             "WHERE {\n" +
                             "    ?conti oxly:consistsOf ?country ;\n" +
                             "           oxly:continentName ?contiName .\n" +
                             "\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "   oxly:hasSustainability ?sus .\n" +
                             "\n" +
                             "    ?well oxly:happinessScore ?happiness_score.\n" +
                             "\n" +
                             "    FILTER(CONTAINS(str(?contiName), \""+ params.get("continent") +"\"))\n" +
                             "}\n" +
                             "GROUP BY ?contiName";
            case "query9" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                             "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                             "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                             "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                             "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                             "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                             "\n" +
                             "SELECT DISTINCT ?countryName ?hdi\n" +
                             "WHERE {\n" +
                             "    ?conti oxly:consistsOf ?country ;\n" +
                             "           oxly:continentName ?contiName .\n" +
                             "\n" +
                             "    ?country oxly:countryName ?countryName ;\n" +
                             "    oxly:hasSustainability ?sus .\n" +
                             "\n" +
                             "    ?sus oxly:countryHDI ?humanDev .\n" +
                             "    ?humanDev oxly:hdiScore ?hdi .\n" +
                             "\n" +
                             "    FILTER(CONTAINS(str(?contiName), \"Asia\"))\n" +
                             "}\n" +
                             "ORDER BY "+ params.get("sort") + "(?hdi)\n" +
                             "LIMIT 1";
            case "query10" -> "prefix rr: <http://www.w3.org/ns/r2rml#>\n" +
                              "prefix geo: <http://www.opengis.net/ont/geosparql#>\n" +
                              "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                              "prefix geo2: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                              "prefix xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                              "prefix oxly: <http://www.example.org/ont/groupK#>\n" +
                              "\n" +
                              "SELECT DISTINCT ?contiName (AVG(?hdi) AS ?hdi)\n" +
                              "WHERE {\n" +
                              "    ?conti oxly:consistsOf ?country ;\n" +
                              "           oxly:continentName ?contiName .\n" +
                              "\n" +
                              "    ?country oxly:countryName ?countryName ;\n" +
                              "    oxly:hasSustainability ?sus .\n" +
                              "\n" +
                              "    ?sus oxly:countryHDI ?humanDev .\n" +
                              "    ?humanDev oxly:hdiScore ?hdi .\n" +
                              "}\n" +
                              "GROUP BY ?contiName\n" +
                              "ORDER BY " + params.get("sort") + "(?hdi)\n" +
                              "LIMIT 1";
            default -> "SELECT * WHERE {?s ?p ?o }";
        };
    }
}
//TODO:
