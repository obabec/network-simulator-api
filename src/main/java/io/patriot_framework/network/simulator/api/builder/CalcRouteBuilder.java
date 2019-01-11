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

import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;

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

    public CalcRouteBuilder viaRouter(Router router) {
        this.router = router;
        return this;
    }

    public CalcRouteBuilder withCost(Integer cost) {
        this.cost = cost;
        return this;
    }

    public CalcRouteBuilder withDestNetwork(Integer targetNetworkIndex) {
        this.destNetwork = targetNetworkIndex;
        return this;
    }

    public CalcRouteBuilder withSourceNetwork(Integer sourceNetwork) {
        this.sourceNetwork = sourceNetwork;
        return this;
    }

    public CalcRouteBuilder addRoute() {
        CalcRoute sR = new CalcRoute(new NextHop(router, destNetwork), cost);
        topologyBuilder.topology.get(sourceNetwork).getCalcRoutes().add(destNetwork, sR);

        if (sourceNetwork != destNetwork) {
            CalcRoute dR = new CalcRoute(new NextHop(router, sourceNetwork), cost);
            topologyBuilder.topology.get(destNetwork).getCalcRoutes().add(sourceNetwork, dR);
        }
        return this;
    }

    /*public NetworkBuilder buildRoutes() {
        CalcRoute cR = new CalcRoute(new NextHop(router, destNetwork), cost);
        networkBuilder.calcRoutes.add(destNetwork, cR);
        return networkBuilder;
    }*/

    public TopologyBuilder buildRoutes() {

        for (int i = 0; i < topologyBuilder.topology.size(); i++) {
            CalcRoute cR = new CalcRoute(new NextHop(null, i), null);
            topologyBuilder.topology.get(i).getCalcRoutes().add(i, cR);
        }

        return topologyBuilder;
    }
}
