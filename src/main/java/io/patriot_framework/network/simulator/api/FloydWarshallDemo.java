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

import io.patriot_framework.network.simulator.api.builder.TopologyBuilder;
import io.patriot_framework.network.simulator.api.manager.NetworkManager;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;

import java.util.ArrayList;
import java.util.HashMap;

public class FloydWarshallDemo {

    public void deploy() {
        HashMap<String, Router> routers = new HashMap<>();

        routers.put("R1", new Router("R1"));

        routers.put("R2", new Router("R2"));

        routers.put("R3", new Router("R3"));

        routers.put("R5", new Router("R5"));

        int routeNeedsCalc = 6;
        ArrayList<Network> topology = new TopologyBuilder(5)
                .withNetwork("N1")
                    .withIP("192.168.0.0")
                    .withMask(28)
                    .create()
                .withNetwork("N2")
                    .withIP("192.168.16.0")
                    .withMask(28)
                    .create()
                .withNetwork("N3")
                    .withIP("192.168.32.0")
                    .withMask(28)
                    .create()
                    .withNetwork("N4")
                        .withIP("192.168.48.0")
                        .withMask(28)
                        .create()
                .withNetwork("internet")
                    .withInternet(true)
                    .create()
                .withRoutes()
                    .withSourceNetwork(0)

                        .withDestNetwork(1)
                        .withCost(1)
                        .viaRouter(routers.get("R1"))
                        .addRoute()

                        .withDestNetwork(2)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                        .withDestNetwork(3)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                        .withDestNetwork(4)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                    .withSourceNetwork(1)

                        .withDestNetwork(2)
                        .withCost(1)
                        .viaRouter(routers.get("R2"))
                        .addRoute()

                        .withDestNetwork(3)
                        .withCost(1)
                        .viaRouter(routers.get("R3"))
                        .addRoute()

                        .withDestNetwork(4)
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                    .withSourceNetwork(2)

                        .withDestNetwork(3)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .addRoute()

                        .withDestNetwork(4)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .addRoute()

                    .withSourceNetwork(3)

                        .withDestNetwork(4)
                        .withCost(1)
                        .viaRouter(routers.get("R5"))
                        .addRoute()

                .buildRoutes()
                .build();



        NetworkManager networkManager = new NetworkManager();

        routers = networkManager.connect(topology, routers);
        networkManager.calcRoutes(topology);
        HashMap hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap, routers);
    }
}
