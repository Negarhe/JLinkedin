package controller.server.Handler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.requests.Request;
import model.DataBase;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public  class SearchHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // This is where you handle the /search endpoint.
        // Read the request body
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        StringBuilder requestBodyBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            requestBodyBuilder.append(line);
        }
        String requestBody = requestBodyBuilder.toString();

        // Parse the request body into a SearchRequest object
        Gson gson = new Gson();
        Request.SearchRequest searchRequest = gson.fromJson(requestBody, Request.SearchRequest.class);
        DataBase db = new DataBase();
        User user = db.searchUser(searchRequest.getQuery());

        String response;
        if (user != null){
            response = "User found";
            exchange.sendResponseHeaders(200, response.length());
        } else {
            response = "User not found";
            exchange.sendResponseHeaders(500, response.length());
        }
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
