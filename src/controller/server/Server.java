package controller.server;

import com.sun.net.httpserver.HttpServer;
import model.Connect;

import java.net.InetSocketAddress;
import java.util.HashSet;

public class Server {

    public static HashSet<Connect> connects = new HashSet<>();

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8085), 0);

        server.createContext("/signup", new Handler.Signup());
        server.createContext("/login", new Handler.Login());


        server.setExecutor(null); // creates a default executor
        server.start();
    }

}