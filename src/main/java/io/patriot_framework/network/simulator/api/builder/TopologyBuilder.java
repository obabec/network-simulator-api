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
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;

import java.util.ArrayList;

public class TopologyBuilder {
    ArrayList<Network> topology;

    public NetworkBuilder withNetwork(String name) {
        return new NetworkBuilder(this, name);
    }

    public TopologyBuilder(int networkCount) {
        topology = new ArrayList<>(networkCount);
    }

    public ArrayList<Network> build() {
        return topology;
    }

    public CalcRouteBuilder withRoutes() {
        return new CalcRouteBuilder(this);
    }


}
