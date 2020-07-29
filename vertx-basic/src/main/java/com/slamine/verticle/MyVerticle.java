package com.slamine.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

/**
 * https://vertx.io/docs/vertx-core/java/#_verticles
 */
public class MyVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        //Do something that take time
        if(true){
            startPromise.complete();
        }else{
            startPromise.fail("Bad thing happened!");
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        new MyVerticle();
    }

    /**
     * If you’re registering event bus handlers from inside verticles,
     * those handlers will be automatically unregistered when the verticle is undeployed.
     */
}
