package controller.server.Handler;

import com.sun.net.httpserver.HttpExchange;
import model.DataBase;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.MockHttpExchange;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShowProfileHandlerTest {

    private DataBase testDataBase;

    @BeforeEach
    void setUp() {
        // Initialize database connection with test credentials
        testDataBase = new DataBase();
    }

    @Test
    void handle_SuccessfulProfileRetrieval() throws IOException {
        // Insert a user directly into the database to simulate an existing user
        String testEmail = "testUser@example.com";
        String testPassword = "1@Am23456";
        testDataBase.insertUser(new User(testEmail, "Test", "User", testPassword));

        // Create a JSON string that represents the request body for a successful profile retrieval
        String requestBodyJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", testEmail, testPassword);

        // Prepare the HttpExchange with a profile retrieval request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        ShowProfileHandler handler = new ShowProfileHandler();
        handler.handle(exchange);

        // Verify the response indicates a successful profile retrieval
        assertEquals(200, ((MockHttpExchange)exchange).getResponseCode());
        Assertions.assertTrue(((MockHttpExchange)exchange).getResponseBodyAsString().contains(testEmail));
    }

    @Test
    void handle_FailedProfileRetrieval() throws IOException {
        // Use a non-existing user's credentials
        String testEmail = "nonExistingUser@example.com";
        String testPassword = "wrongPassword";

        // Create a JSON string that represents the request body for a failed profile retrieval
        String requestBodyJson = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", testEmail, testPassword);

        // Prepare the HttpExchange with a profile retrieval request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        ShowProfileHandler handler = new ShowProfileHandler();
        handler.handle(exchange);

        // Verify the response indicates a failed profile retrieval
        assertEquals(500, ((MockHttpExchange)exchange).getResponseCode());
        assertEquals("User not found", ((MockHttpExchange)exchange).getResponseBodyAsString());
    }

    private HttpExchange createHttpExchange(String requestBody) {
        return new MockHttpExchange(requestBody);
    }
}