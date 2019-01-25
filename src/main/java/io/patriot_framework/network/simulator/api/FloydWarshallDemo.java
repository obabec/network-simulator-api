/*
 * Copyright 2019 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.network.simulator.api;

import io.patriot_framework.network.simulator.api.builder.NetworkBuilder;
import io.patriot_framework.network.simulator.api.builder.TopologyBuilder;
import io.patriot_framework.network.simulator.api.manager.NetworkManager;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FloydWarshallDemo {

    public void deploy() {
        HashMap<String, Router> routers = new HashMap<>();

        routers.put("R1", new Router("R1"));

        routers.put("R2", new Router("R2"));

        routers.put("R3", new Router("R3"));

        routers.put("R5", new Router("R5"));

        Network n1 = new NetworkBuilder("N1")
                .withIP("192.168.0.0")
                .withMask(28)
                .build();

        Network n2 = new NetworkBuilder("N2")
                .withIP("192.168.16.0")
                .withMask(28)
                .build();

        Network n3 = new NetworkBuilder("N3")
                .withIP("192.168.32.0")
                .withMask(28)
                .build();

        Network n4 = new NetworkBuilder("N4")
                .withIP("192.168.48.0")
                .withMask(28)
                .build();

        Network internet = new NetworkBuilder("Internet")
                .withInternet(true)
                .build();
        int routeNeedsCalc = 6;
        ArrayList<Network> topology = new TopologyBuilder(5)
                .withNetwork("N1")
                    .withIP("192.168.0.0")
                    .withMask(28)
                    .withCalcRoute()
                        .withDestNetwork(0)
                        .withCost(null)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(1)
                        .withCost(1)
                        .viaRouter(routers.get("R1"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(2)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(3)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(4)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .create()
                .withNetwork("N2")
                    .withIP("192.168.16.0")
                    .withMask(28)
                    .withCalcRoute()
                        .withDestNetwork(0)
                        .withCost(1)
                        .viaRouter(routers.get("R1"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(1)
                        .withCost(null)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(2)
                        .withCost(1)
                        .viaRouter(routers.get("R2"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(3)
                        .withCost(1)
                        .viaRouter(routers.get("R3"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(4)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .create()
                .withNetwork("N3")
                    .withIP("192.168.32.0")
                    .withMask(28)
                    .withCalcRoute()
                        .withDestNetwork(0)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(1)
                        .withCost(1)
                        .viaRouter(routers.get("R2"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(2)
                        .withCost(null)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(3)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(4)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .build()
                    .create()
                    .withNetwork("N4")
                        .withIP("192.168.48.0")
                        .withMask(28)
                        .withCalcRoute()
                            .withDestNetwork(0)
                            .withCost(routeNeedsCalc)
                            .viaRouter(null)
                            .build()
                        .withCalcRoute()
                            .withDestNetwork(1)
                            .withCost(1)
                            .viaRouter(routers.get("R3"))
                            .build()
                        .withCalcRoute()
                            .withDestNetwork(2)
                            .withCost(1)
                            .viaRouter(routers.get("R5"))
                            .build()
                        .withCalcRoute()
                            .withDestNetwork(3)
                            .withCost(null)
                            .viaRouter(null)
                            .build()
                        .withCalcRoute()
                            .withDestNetwork(4)
                            .withCost(1)
                            .viaRouter(routers.get("R5"))
                            .build()
                        .create()
                .withNetwork("internet")
                    .withInternet(true)
                    .withCalcRoute()
                        .withDestNetwork(0)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(1)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(2)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(3)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .build()
                    .withCalcRoute()
                        .withDestNetwork(4)
                        .withCost(null)
                        .viaRouter(null)
                        .build()
                    .create()
                .build();
        NetworkManager networkManager = new NetworkManager();

        routers = networkManager.connect(topology, routers);
        networkManager.calcRoutes(topology);
        HashMap hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap, routers);
    }
}
