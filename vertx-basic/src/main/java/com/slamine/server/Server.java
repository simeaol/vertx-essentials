package com.slamine.server;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.net.NetServer;
import io.vertx.core.net.NetServerOptions;

public class Server {

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, event -> {
            Vertx vertx = event.result();
            NetServer netServer = vertx.createNetServer(new NetServerOptions()
                    .setHost("localhost")
                    .setPort(3333)
                    .setLogActivity(true));

            netServer.connectHandler(socket -> {
                socket.handler(buffer -> {
                    System.out.println("Received data from socket: " + buffer);

                    socket.write("Sending data from socket","UTF-8", res -> {
                        if(res.succeeded()){
                            System.out.println("socket sent to listeners...");
                        }
                    });
                });

                socket.closeHandler(s -> {
                    System.out.println("Socket has been closed." + s);
                });
            });


            HttpServer httpServer = vertx.createHttpServer();

            httpServer.requestHandler(req -> {
                HttpServerResponse response = req.response();
                response.end("Hello world!");
            });

            /**
             * Server Push is new feature of HTTP2 that enables sending multiple response in parallel for a single client request
             */
            httpServer.requestHandler(req -> {
                HttpServerResponse response = req.response();
                response.push(HttpMethod.GET, "/index.html", resp -> {
                    if(resp.succeeded()){
                        HttpServerResponse result = resp.result();
                        result.putHeader("content-type", "application/json")
                                .end("multiple response");
                    }else{
                        System.out.println("Could not push client resource.");
                    }
                });
            });

        });
    }
}
