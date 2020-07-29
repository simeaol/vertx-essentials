package com.slamine.manager.cluster;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

public class MyClusterManager {

    public static void main(String[] args) {
        ClusterManager clusterManager = new HazelcastClusterManager();

        EventBusOptions eventBusOptions = new EventBusOptions()
                .setHost("localhost")
                .setPort(9000)
                .setLogActivity(true);

        VertxOptions options = new VertxOptions()
                .setMetricsOptions(new MetricsOptions()
                .setEnabled(true))
                .setEventBusOptions(eventBusOptions)
                .setClusterManager(clusterManager)
                ;

        Vertx.clusteredVertx(options, res -> {
            if(res.succeeded()){
                Vertx vertx = res.result();
            }else{
                //failed
            }
        });
    }
}
