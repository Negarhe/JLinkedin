package controller.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import controller.server.Handler.*;
import model.Connect;
import model.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashSet;

public class Server {

    public static HashSet<Connect> connects = new HashSet<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        server.createContext("/signup", new SignupHandle());
        server.createContext("/login", new LoginHandle());
        server.createContext("/search", new SearchHandle());
        server.createContext("/showProfile", new ShowProfileHandle());
        server.createContext("/follow", new FollowHandle());
        server.createContext("/showFollowers", new ShowFollowersHandle());
        server.createContext("/showFollowing", new ShowFollowingHandle());
        server.createContext("/post", new postHandler());
        server.createContext("/showFeed", new showFeedHandler());


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