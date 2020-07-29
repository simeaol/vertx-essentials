package com.slamine.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.RequestOptions;
import io.vertx.core.http.impl.HttpClientImpl;

public class ClientRequest extends AbstractVerticle {

    public void sendHttp(){
        HttpClientOptions options = new HttpClientOptions()
                .setDefaultHost("localhost")
                .setDefaultPort(3000)
                .setConnectTimeout(10000)
                .setLogActivity(true);

        RequestOptions ops = new RequestOptions().setHost("localhost").setPort(3000);



        HttpClient httpClient = vertx.createHttpClient(options);
        httpClient.getNow("/hello", resp-> {
            resp.statusCode();
        });

        httpClient.request(HttpMethod.GET, ops, res -> {

        });
    }

}
