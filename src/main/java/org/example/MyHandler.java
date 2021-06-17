package org.example;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

public class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
        Headers requestHeaders = t.getRequestHeaders();
        String responseBody = buildResponseBody(requestHeaders);
        sendResponse(t, responseBody);
    }

    private void sendResponse(HttpExchange t, String responseBody) throws IOException {
        Headers responseHeaders = t.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        t.sendResponseHeaders(200, responseBody.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = t.getResponseBody();
        os.write(responseBody.getBytes());
        os.close();
    }

    private String buildResponseBody(Headers requestHeaders) {
        Set<String> keys = requestHeaders.keySet();
        String s = keys.stream().map(key -> key + ": " + requestHeaders.get(key) + "\n").collect(Collectors.joining());
        return s;
    }
}
