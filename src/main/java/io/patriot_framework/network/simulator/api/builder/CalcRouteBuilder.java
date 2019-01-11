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

package io.patriot_framework.network.simulator.api.builder;

import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;

import java.util.ArrayList;

public class CalcRouteBuilder {
    Router router;
    Integer sourceNetwork;
    Integer cost;
    Integer destNetwork;
    NetworkBuilder networkBuilder;
    TopologyBuilder topologyBuilder;

    public CalcRouteBuilder(TopologyBuilder topologyBuilder) {
        this.topologyBuilder = topologyBuilder;
    }

    public CalcRouteBuilder(NetworkBuilder networkBuilder) {
        this.networkBuilder = networkBuilder;
    }

    public CalcRouteBuilder viaRouter(String routerName) {
        this.router = topologyBuilder.topology.getRouters().get(routerName);
        return this;
    }

    public CalcRouteBuilder withCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public CalcRouteBuilder withDestNetwork(String targetNetworkName) {
        this.destNetwork = findNetworkByName(targetNetworkName);
        return this;
    }

    public CalcRouteBuilder withDestNetwork(Integer targetNetworkIndex) {
        this.destNetwork = targetNetworkIndex;
        return this;
    }

    public CalcRouteBuilder withSourceNetwork(String sourceNetworkName) {
        this.sourceNetwork = findNetworkByName(sourceNetworkName);
        return this;
    }

    private Integer findNetworkByName(String name) {
        ArrayList<Network> networks = topologyBuilder.topology.getNetworkTop();
        for (int i = 0; i < networks.size(); i++) {
            if (networks.get(i).getName() == name) {
                return i;
            }
        }
        return null;
    }

    public CalcRouteBuilder withSourceNetwork(Integer sourceNetwork) {
        this.sourceNetwork = sourceNetwork;
        return this;
    }

    public CalcRouteBuilder addRoute() {
        CalcRoute sR = new CalcRoute(new NextHop(router, destNetwork), cost);
        topologyBuilder.topology.getNetworkTop().get(sourceNetwork).getCalcRoutes().add(destNetwork, sR);

        if (sourceNetwork != destNetwork) {
            CalcRoute dR = new CalcRoute(new NextHop(router, sourceNetwork), cost);
            topologyBuilder.topology.getNetworkTop().get(destNetwork).getCalcRoutes().add(sourceNetwork, dR);
        }
        return this;
    }

    public TopologyBuilder buildRoutes() {

        for (int i = 0; i < topologyBuilder.topology.getNetworkTop().size(); i++) {
            CalcRoute cR = new CalcRoute(new NextHop(null, i), null);
            topologyBuilder.topology.getNetworkTop().get(i).getCalcRoutes().add(i, cR);
        }

        return topologyBuilder;
    }
}
