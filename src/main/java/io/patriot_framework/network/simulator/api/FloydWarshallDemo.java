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
import io.patriot_framework.network.simulator.api.manager.Manager;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network_simulator.docker.control.DockerController;
import io.patriot_framework.network.simulator.api.model.devices.application.Application;
import java.util.Arrays;


public class FloydWarshallDemo {

    public void deploy() {

        int routeNeedsCalc = 6;
        Topology topology = new TopologyBuilder(5)
                .withRouters()
                        .withName("R1")
                        .withCreator("Docker")
                    .createRouter()
                        .withName("R2")
                        .withCreator("Docker")
                    .createRouter()
                        .withName("R3")
                        .withCreator("Docker")
                    .createRouter()
                        .withName("R5")
                        .withCreator("Docker")
                    .createRouter()
                    .addRouters()
                .withNetwork("N1")
                    .withIP("192.168.0.0")
                    .withMask(28)
                    .withCreator("Docker")
                    .create()
                .withNetwork("N2")
                    .withIP("192.168.16.0")
                    .withMask(28)
                    .withCreator("Docker")
                    .create()
                .withNetwork("N3")
                    .withIP("192.168.32.0")
                    .withMask(28)
                    .withCreator("Docker")
                    .create()
                .withNetwork("N4")
                    .withIP("192.168.48.0")
                    .withMask(28)
                    .withCreator("Docker")
                    .create()
                .withNetwork("internet")
                    .withInternet(true)
                    .withCreator("Docker")
                    .create()
                .withRoutes()
                    .withSourceNetwork("N1")

                        .withDestNetwork("N2")
                        .withCost(1)
                        .viaRouter("R1")
                        .addRoute()

                        .withDestNetwork("N3")
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                        .withDestNetwork("N4")
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                        .withDestNetwork("internet")
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                    .withSourceNetwork("N2")

                        .withDestNetwork("N3")
                        .withCost(1)
                        .viaRouter("R2")
                        .addRoute()

                        .withDestNetwork("N4")
                        .withCost(1)
                        .viaRouter("R3")
                        .addRoute()

                        .withDestNetwork("internet")
                        .withCost(routeNeedsCalc)
                        .viaRouter(null)
                        .addRoute()

                    .withSourceNetwork("N3")

                        .withDestNetwork("N4")
                        .withCost(1)
                        .viaRouter("R5")
                        .addRoute()

                        .withDestNetwork("internet")
                        .withCost(1)
                        .viaRouter("R5")
                        .addRoute()

                    .withSourceNetwork("N4")

                        .withDestNetwork("internet")
                        .withCost(1)
                        .viaRouter("R5")
                        .addRoute()

                    .buildRoutes()
                .withRoutersTag("patriot")
                .build();
        Manager networkManager = new Manager(Arrays.asList(new DockerController()));
        networkManager.setMonitoring("172.22.0.1", 9200);
        networkManager.deployTopology(topology);

        Application application = new Application("App", "Docker");
        networkManager.deployDevice(application, "patr");
        //TODO: pripojeni siti k aplikaci, filebeat,
    }
}
