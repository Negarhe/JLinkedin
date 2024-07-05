package util;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MockHttpExchange extends HttpExchange {
    private final InputStream requestBody;
    private final ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
    private final Headers requestHeaders = new Headers();
    private final Headers responseHeaders = new Headers();
    private int responseCode = 0;

    public MockHttpExchange(String requestBody) {
        this.requestBody = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Headers getRequestHeaders() {
        return requestHeaders;
    }

    @Override
    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    @Override
    public InputStream getRequestBody() {
        return requestBody;
    }

    @Override
    public OutputStream getResponseBody() {
        return responseBody;
    }

    @Override
    public void sendResponseHeaders(int rCode, long responseLength) {
        this.responseCode = rCode;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return null;
    }

    // Implement other abstract methods as needed, possibly as no-ops or simple stubs

    @Override
    public URI getRequestURI() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRequestMethod() {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpContext getHttpContext() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() {
        // Close or flush streams if necessary
    }

    // Add getters for testing assertions, e.g., getResponseCode, getResponseBodyAsString

    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public String getProtocol() {
        return "";
    }

    @Override
    public Object getAttribute(String name) {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {

    }

    @Override
    public void setStreams(InputStream i, OutputStream o) {

    }

    @Override
    public HttpPrincipal getPrincipal() {
        return null;
    }

    public String getResponseBodyAsString() {
        return responseBody.toString(StandardCharsets.UTF_8);
    }
}