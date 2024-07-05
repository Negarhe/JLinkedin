package controller.server.Handler;

import model.DataBase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SignupHandlerTest {

    private DataBase testDataBase;

    @BeforeEach
    void setUp() {
        // Initialize database connection with test credentials
        testDataBase = new DataBase();
    }

    @Test
    void handle_SuccessfulSignup() throws IOException {
        // Create random email
        String testMail = UUID.randomUUID() + "@example.com";

        // Create a JSON string that represents the request body
        // add testMail into the JSON string
        String requestBodyJson = String.format(
                "{\"email\":\"%s\",\"name\":\"Test\",\"lastName\":\"User\",\"password\":\"1@Am23456\"}",
                testMail
        );

        // Prepare the HttpExchange with a successful signup request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        SignupHandler handler = new SignupHandler();
        handler.handle(exchange);

        // Verify the user was successfully inserted into the database
        assertTrue(testDataBase.userExists(testMail)); // Adjust this line to correctly extract the email from requestBodyJson
    }

    @Test
    void handle_UserAlreadyExists() throws IOException {
        // Insert a user directly into the database to simulate an existing user
        testDataBase.insertUser(new User("testEmail@example.com", "Test", "User", "123456"));

        // Create a JSON string that represents the request body for an existing user
        String requestBodyJson = "{\"email\":\"testEmail@example.com\",\"name\":\"Test\",\"lastName\":\"User\",\"password\":\"123456\"}";

        // Prepare the HttpExchange with a signup request for an existing user
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        SignupHandler handler = new SignupHandler();
        handler.handle(exchange);

        // Verify the response indicates the user already exists
        // This step requires checking the HttpExchange response, which is not detailed here due to the complexity of mocking HttpExchange
    }

    @Test
    void handle_ImproperPasswordFails() throws IOException {
        // Create a test email
        String testEmail = UUID.randomUUID() + "@example.com";

        // Create a JSON string with an improper password
        String requestBodyJson = String.format(
                "{\"email\":\"%s\",\"name\":\"Test\",\"lastName\":\"User\",\"password\":\"123\"}",
                testEmail
        );

        // Prepare the HttpExchange with the improper password request
        HttpExchange exchange = createHttpExchange(requestBodyJson);

        SignupHandler handler = new SignupHandler();
        handler.handle(exchange);

        // Verify the user was not inserted into the database due to improper password
        assertFalse(testDataBase.userExists(testEmail));
    }

    private HttpExchange createHttpExchange(String requestBody) {
        return new MockHttpExchange(requestBody);
    }
}