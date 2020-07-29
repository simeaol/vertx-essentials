package com.slamine.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class MyPublisher extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

    }

    /**
     * Message will be delivered to all handlers registered agains the address {channel.1}
     */
    public void publishMessage(){
        EventBus eventBus = vertx.eventBus();

        DeliveryOptions deliveryOptions = new DeliveryOptions()
                .addHeader("header", "my header")
                .setSendTimeout(10000);

        eventBus.publish("channel.1", "<publish> Hello World from <publish.!", deliveryOptions);
    }

    /**
     * Message will be delivered just to only one handler registered at the address {message.1}
     * The handler is chosen using non-stric round-robin fashion
     */
    public void sendMessage(){
        EventBus eventBus = vertx.eventBus();

        DeliveryOptions deliveryOptions = new DeliveryOptions()
                .addHeader("header", "my header")
                .setSendTimeout(10000);
        eventBus.send("message.1", "Hello message from <send>!", res -> {
            if(res.succeeded()){
                System.out.println("Message received and processed. The ack is=" + res.result().body());
            }else{

                System.out.println("None handler processed the message. fail=" + res.cause().getMessage());
            }
        });
    }

    public void sendRequestMessage(){
        EventBus eventBus = vertx.eventBus();

        DeliveryOptions deliveryOptions = new DeliveryOptions()
                .addHeader("header", "my header")
                .setSendTimeout(10000);
        eventBus.request("message.1", "Hello message from <send>!", res -> {
            if(res.succeeded()){
                System.out.println("Message received and processed. The ack is=" + res.result().body());
            }else{

                System.out.println("None handler processed the message. fail=" + res.cause().getMessage());
            }
        });
    }
}
