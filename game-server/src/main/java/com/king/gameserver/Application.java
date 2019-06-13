package com.king.gameserver;

import com.king.gameserver.api.router.RouteHandler;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import static com.king.gameserver.Injection.provideExceptionHandler;
import static com.king.gameserver.Injection.provideUserController;

public class Application {

    public static void main(String[] args) throws IOException {

        final int serverPort = 8000;
        final HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        final RouteHandler routeHandler = new RouteHandler(provideUserController(), provideExceptionHandler());

        final HttpContext context = server.createContext("/api/hello", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String respText = "Hello\n!";
                exchange.sendResponseHeaders(200, respText.getBytes().length);
                OutputStream output = exchange.getResponseBody();
                output.write(respText.getBytes());
                output.flush();
            } else {
                exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
            }
            exchange.close();
        }));

        server.createContext("/", routeHandler::handle);

        server.setExecutor(null); // creates a default executor
        server.start();
    }
}
