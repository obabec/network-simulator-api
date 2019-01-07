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

public class CalcRouteBuilder {
    Router router;
    Integer cost;
    Integer targetNetworkIndex;
    NetworkBuilder networkBuilder;

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
        this.targetNetworkIndex = targetNetworkIndex;
        return this;
    }

    public NetworkBuilder build() {
        CalcRoute cR = new CalcRoute(new NextHop(router, targetNetworkIndex), cost);
        networkBuilder.calcRoutes.add(targetNetworkIndex, cR);
        return networkBuilder;
    }
}
