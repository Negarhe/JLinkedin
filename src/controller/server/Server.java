package controller.server;

import com.sun.net.httpserver.HttpServer;
import model.Connect;
import model.User;

import java.net.InetSocketAddress;
import java.util.HashSet;

public class Server {

    public static HashSet<Connect> connects = new HashSet<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        server.createContext("/signup", new Handler.Signup());
        server.createContext("/login", new Handler.Login());
        server.createContext("/search", new Handler.Search());


        server.setExecutor(null); // creates a default executor
        server.start();
    }

    public void sendRequest(User user, User searchedUser, String note) {
        Connect connect = new Connect(user.getEmail(), searchedUser.getEmail(), note);
        connects.add(connect);
    }

    public void showRequest(String email) {
        for (Connect connect : connects) {
            if (connect.getReceiver().equals(email)) {
                System.out.println("You have a request from " + connect.getSender());
                System.out.println("Note: " + connect.getNote());
            }
        }
    }
}