package com.slamine.eventbus.clustered;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.ClientAuth;
import io.vertx.core.net.JksOptions;

/**
 * If you’re creating your Vert.x instance programmatically you get a clustered event bus by configuring the Vert.x instance as clustered;
 *
 * You can run Vert.x clustered manually by using: vertx run my-ClusterProgrammatically.java -cluster
 */
public class ClusterProgrammatically {

    public static void main(String[] args) {
        VertxOptions options = new VertxOptions()
                .setEventBusOptions(new EventBusOptions()
                .setSsl(false)
                .setKeyStoreOptions(new JksOptions()
                        .setPath("keystore.jks")
                .setPassword("password"))
                .setTrustStoreOptions(new JksOptions()
                .setPath("truststore.jks")
                .setPassword("password"))
                .setClientAuth(ClientAuth.NONE));

        Vertx.clusteredVertx(options, event -> {
            if(event.succeeded()){
                Vertx vertx = event.result();
                System.out.println("Clustered event bus vert.x ");
            }else{
                //Failed
                System.out.println("Failed: " + event.cause());
            }
        });
    }
}
