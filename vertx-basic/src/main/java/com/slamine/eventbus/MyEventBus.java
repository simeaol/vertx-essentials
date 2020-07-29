package com.slamine.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.Json;

public class MyEventBus extends AbstractVerticle {

    @Override
    public void start(Promise<Void> promise) throws Exception {
        EventBus eventBus = vertx.eventBus();

        //vertx.getOrCreateContext().

        MessageConsumer<Object> consumer = eventBus.consumer("hello.world", message -> {
            Object body = message.body();
            String data = Json.decodeValue(body.toString(), String.class);
            System.out.println("Received data=" + data);
        });
        
//        eventBus.consumer("mybus-unregister", this::unregister).unregister(event -> {
//            if(event.succeeded()){
//                System.out.println("MyBus unregistered");
//            }else{
//                System.out.println("Cannot unregister MyBus");
//            }
//        });

        consumer.completionHandler(event -> {
            if(event.succeeded()){
                promise.complete();
                System.out.println("MyEventBus registered handler has reached all nodes");
            }else{
                System.out.println("MyEventBus was not registered successful");
            }
        });

    }

    private <T> void unregister(Message<T> tMessage) {
        System.out.println("Unregistering MyBus asked..." + tMessage.body());
    }


}
