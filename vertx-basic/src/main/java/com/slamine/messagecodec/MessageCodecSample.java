package com.slamine.messagecodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.impl.codecs.JsonObjectMessageCodec;
import io.vertx.core.json.JsonObject;

public class MessageCodecSample extends AbstractVerticle {

    /**
     * Apply codec for message
     */
    public void messageCodec(){
        DeliveryOptions options = new DeliveryOptions()
                .setCodecName("my-codec-name");

        vertx.eventBus()
                .request("pojo.message", "Message Codec <JsonObject>", options, res -> {
                    //Do something
                });
    }

    /**
     * Register message codec for particular type (JsonObject).
     * Then don't have to specify the codec on each send in the delivery options
     */
    public void messgeCodec(){
        vertx.eventBus()
                .registerDefaultCodec(JsonObject.class, new JsonObjectMessageCodec())
                .request("pojo.message", "Message Codec <JsonObject>", res -> {
                    //Do something
                });
    }

    public void unregister(){
        vertx.eventBus().
                unregisterCodec("my-codec-name");
    }
}
