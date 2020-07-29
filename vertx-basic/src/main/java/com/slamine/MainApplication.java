package com.slamine;

import com.slamine.verticle.MyVerticle;
import io.vertx.core.*;
import io.vertx.core.http.HttpServer;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

public class MainApplication {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(
                new VertxOptions()
                .setWorkerPoolSize(40)
        );

        //https://vertx.io/docs/vertx-core/java/#_executing_periodic_and_delayed_actions
        //One-shot Timers
        vertx.setPeriodic(10000l, id -> {
            System.out.println(id);//Timer ID
        });

        //Periodic Timers
        int id = 0;
        vertx.setPeriodic(1000l, event -> {
            System.out.println(id);//Timer ID
        });

       // vertx.cancelTimer(timerId);

        vertx.executeBlocking(promise -> {
            //Do Blocking things
            //Thread.sleep(10000);
            promise.complete();
        }, asyncResult -> {
            //Return response of blocking things asynchronous
        });

        vertx.createSharedWorkerExecutor("My-worker-pool", 40, 10, TimeUnit.MINUTES)
                .executeBlocking(promise -> {
                    //Do blocking things
                    promise.complete("Finished");
                },
                        true,//ordered matters ?
                        asyncResult -> {
                    //Return blocking things results
                        });

        HttpServer server = vertx.createHttpServer()
                .listen(3000, "localhost", event -> {
                    if(event.succeeded()){
                        System.out.println("Server listening in port 3000");
                    }
                });

        server.requestHandler(request -> {
            request.response().end("Hello world!");
        });



        Future<String> future1 = Future.future(e -> Optional.of("Hello,"));
        Future<String> future2 = Future.future(e -> Optional.of("World!"));
        CompositeFuture.all(future1, future2)
                .onComplete(e -> {
                    if(e.succeeded()){
                        //All future success
                    }else{
                        //At least one future failed
                    }
                });

        //Composite
        future1.compose(v -> {
            //Do something
            return future2;
        }).compose(v2 -> {
            //Do something
            return Future.future(Promise::complete);
        });

        CompletionStage stage = CompletableFuture.completedStage("Completed!");
        Future.fromCompletionStage(stage, vertx.getOrCreateContext())
                .flatMap(o -> {
                    String key = UUID.randomUUID().toString();
                    System.out.println(key);
                    //do something
                    return Future.future(Promise::complete);
                })
                .onSuccess(event -> {
                    //Success
                }).onFailure(event -> {
                    //Failure
                });

        //Deploy Verticle as a worker verticle
        DeploymentOptions options = new DeploymentOptions().setWorker(true).setInstances(1);
        vertx.deployVerticle(new MyVerticle(), r -> {
            if(r.succeeded()){
                r.result();//Deployment ID
                //Vertical Deployment success
            }else{
                //Deployment failed
            }
        });
        //or by name: vertx.deployVerticle(MyVerticle.class.getName(), options);
        // from different language: vertx.deployVerticle("verticles/myverticles.js")



    }
}
