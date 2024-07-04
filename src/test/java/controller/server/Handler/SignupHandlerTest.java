package controller.server.Handler;

import com.sun.net.httpserver.HttpExchange;
import model.DataBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.*;

class SignupHandlerTest {
    private SignupHandler signupHandler;
    private HttpExchange httpExchange;
    private DataBase dataBase;

    @BeforeEach
    void setUp() {
        signupHandler = new SignupHandler();
        httpExchange = mock(HttpExchange.class);
        dataBase = mock(DataBase.class);
    }

    @Test
    void handle_SignupSuccess() throws IOException {
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("{\"email\":\"test@test.com\",\"name\":\"test\",\"lastName\":\"test\",\"password\":\"test\"}".getBytes()));
        when(dataBase.userExists(anyString())).thenReturn(false);
        OutputStream outputStream = mock(OutputStream.class);
        when(httpExchange.getResponseBody()).thenReturn(outputStream);

        signupHandler.handle(httpExchange);

        verify(httpExchange).sendResponseHeaders(200, "Signed up successfully".length());
    }

    @Test
    void handle_EmailExists() throws IOException {
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("{\"email\":\"test@test.com\",\"name\":\"test\",\"lastName\":\"test\",\"password\":\"test\"}".getBytes()));
        when(dataBase.userExists(anyString())).thenReturn(true);
        OutputStream outputStream = mock(OutputStream.class);
        when(httpExchange.getResponseBody()).thenReturn(outputStream);

        signupHandler.handle(httpExchange);

        verify(httpExchange).sendResponseHeaders(409, "Email already exists".length());
    }

    @Test
    void handle_SignupFail() throws IOException {
        when(httpExchange.getRequestBody()).thenReturn(new ByteArrayInputStream("{\"email\":\"test@test.com\",\"name\":\"test\",\"lastName\":\"test\",\"password\":\"test\"}".getBytes()));
        when(dataBase.userExists(anyString())).thenReturn(false);
        OutputStream outputStream = mock(OutputStream.class);
        when(httpExchange.getResponseBody()).thenReturn(outputStream);

        signupHandler.handle(httpExchange);

        verify(httpExchange).sendResponseHeaders(500, "Failed to sign up".length());
    }
}