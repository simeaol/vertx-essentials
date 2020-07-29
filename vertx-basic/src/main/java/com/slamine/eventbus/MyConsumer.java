package com.slamine.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;

public class MyConsumer extends AbstractVerticle {

    public void consume(){
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("message1", message -> {
            System.out.println("Received message="+ message);
        });
    }

    /**
     * The default DeliveryOptions timeout on reply is 30 seconds
     */
    public void consumeAndReply(){
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("message1", message -> {
            System.out.println("Received message="+ message);

            message.reply("Hi,I'm MyConsumer. I received your message!", res ->{
                if(res.succeeded()){
                    res.result().body();
                    System.out.println("Publisher received the reply");
                }else{
                    System.out.println("There are no handlers available to send the message to OR the recipient has explicitly" +
                            "failed the message using fail");
                }
            });
        });
    }

    public void replyAndRequest(){
        EventBus eventBus = vertx.eventBus();

        eventBus.consumer("message1", message -> {
            System.out.println("Received message="+ message);

            message.replyAndRequest("Hi,I'm MyConsumer. I received your message!", res ->{
                if(res.succeeded()){
                    res.result().body();
                    System.out.println("Publisher received the reply");
                }else{
                    System.out.println("There are no handlers available to send the message to OR the recipient has explicitly" +
                            "failed the message using fail");
                }
            });
        });
    }
}
