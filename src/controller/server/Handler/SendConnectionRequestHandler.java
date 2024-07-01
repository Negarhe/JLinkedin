package controller.server.Handler;

import controller.server.requests.Request;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.server.Server;
import model.Connect;
import model.DataBase;
import model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class SendConnectionRequestHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Read the request body

        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder requestBodyBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                requestBodyBuilder.append(line);
            }
            String requestBody = requestBodyBuilder.toString();

            // Parse the request body into a SendConnectionRequest object
            Gson gson = new Gson();
            Request.SendConnectionRequest sendConnectionRequest;
            try {
                sendConnectionRequest = gson.fromJson(requestBody, Request.SendConnectionRequest.class);
            } catch (JsonSyntaxException e) {
                System.out.println("Invalid JSON string: " + e.getMessage());
                return;
            }

            DataBase dataBase = new DataBase();
            User senderUser = dataBase.searchUserWithEmail(sendConnectionRequest.getSenderEmail());
            if (senderUser == null) {
                // Handle the case where the user does not exist
                String response = "Sender not found";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                return;
            } else {
                String sender = senderUser.getEmail();
                User receiverUser = dataBase.searchUserWithEmail(sendConnectionRequest.getReceiverEmail());

                if (receiverUser == null) {
                    String response = "Sender not found";
                    exchange.sendResponseHeaders(404, response.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                    return;
                }

                String receiver = receiverUser.getEmail();
                String note = sendConnectionRequest.getConnectionNote();
                Connect connect = new Connect(sender, receiver, note);


                //add this to dataBase
                dataBase.insertConnectionRequest(connect);


                // Send a response back to the client indicating whether the request was successful or not
                String response = "Connection request sent successfully";
                System.out.println(response);
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }

        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
