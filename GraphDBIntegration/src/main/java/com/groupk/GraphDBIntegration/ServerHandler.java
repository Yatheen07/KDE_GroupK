package com.groupk.GraphDBIntegration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.*;

public class ServerHandler {

    private static Map<String, String> queryToMap(String query) {
        if(query == null) {
            return null;
        }
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            }else{
                result.put(entry[0], "");
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/executeQuery", new SPARQLQueryHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class SPARQLQueryHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            System.out.println("Request received");
            String response = "Request Received";
            try{
                String requestQuery = t.getRequestURI().getRawQuery();
                System.out.println(requestQuery);
                Map<String, String> params = queryToMap(requestQuery);
                if(params != null){
                    String queryNumber  = params.get("queryID").toString();
                    new QueryHandler().executeQuery(queryNumber,params);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

            t.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
