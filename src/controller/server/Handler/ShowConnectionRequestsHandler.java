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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ShowConnectionRequestsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            //parse the request body into a string
            DataBase db = new DataBase();
            Gson gson = new Gson();
            Request.ShowConnectionRequests  showConnectionRequests = gson.fromJson(requestBody, Request.ShowConnectionRequests.class);
            User user = db.searchUserWithEmail(showConnectionRequests.getEmail());

            if (user == null) {
                String response = "invalid email";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            ArrayList<User> connectionRequests = db.getConnectionRequests(user.getEmail());

            if (connectionRequests.isEmpty()) {
                String response = "there is no requests to show";
                exchange.sendResponseHeaders(500, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            }

            String response = new Gson().toJson(connectionRequests);
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
