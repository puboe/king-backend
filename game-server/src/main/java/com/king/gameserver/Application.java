package com.king.gameserver;

import com.king.gameserver.api.router.RouteHandler;
import com.king.gameserver.config.Configurations;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.king.gameserver.Injection.provideExceptionHandler;
import static com.king.gameserver.Injection.provideScoreController;
import static com.king.gameserver.Injection.provideUserController;

public class Application {

    public static void main(String[] args) throws IOException {

        final int serverPort = Configurations.SERVER_PORT;
        final HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);

        final RouteHandler routeHandler =
                new RouteHandler(provideUserController(), provideScoreController(), provideExceptionHandler());

        server.createContext("/", routeHandler::handle);

        server.setExecutor(null);
        server.start();
    }
}
