package controller.server.Handler;

import com.sun.net.httpserver.HttpExchange;
import model.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginHandlerTest {

    private DataBase testDataBase;

    @BeforeEach
    void setUp() {
        // Initialize database connection with test credentials
        testDataBase = new DataBase();
    }

    @Test
    void handle_SuccessfulLogin() throws IOException {
        // Insert a user directly into the database to simulate an existing user
        String testEmail = "testUser@example.com";
        String testPassword = "1@Am23456";
        testDataBase.insertUser(new User(testEmail, "Test", "User", testPassword));

        // Create a JSON string that represents the request body for a successful login
        String requestBodyJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", testEmail, testPassword);

        // Prepare the HttpExchange with a login request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        LoginHandler handler = new LoginHandler();
        handler.handle(exchange);

        // Verify the response indicates a successful login
        assertEquals(200, ((MockHttpExchange)exchange).getResponseCode());
        assertEquals("Logged in successfully", ((MockHttpExchange)exchange).getResponseBodyAsString());
    }

    @Test
    void handle_FailedLogin() throws IOException {
        // Use a non-existing user's credentials
        String testEmail = "nonExistingUser@example.com";
        String testPassword = "wrongPassword";

        // Create a JSON string that represents the request body for a failed login
        String requestBodyJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", testEmail, testPassword);

        // Prepare the HttpExchange with a login request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        LoginHandler handler = new LoginHandler();
        handler.handle(exchange);

        // Verify the response indicates a failed login
        assertEquals(500, ((MockHttpExchange)exchange).getResponseCode());
        assertEquals("Failed to log in", ((MockHttpExchange)exchange).getResponseBodyAsString());
    }

    private HttpExchange createHttpExchange(String requestBody) {
        return new MockHttpExchange(requestBody);
    }
}