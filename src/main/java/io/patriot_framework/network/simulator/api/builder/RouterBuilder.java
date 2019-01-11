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
import io.patriot_framework.network.simulator.api.model.Topology;

public class RouterBuilder {

    TopologyBuilder topologyBuilder;
    String name;


    public RouterBuilder(TopologyBuilder topologyBuilder) {
        this.topologyBuilder = topologyBuilder;
    }

    public RouterBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public RouterBuilder createRouter() {
        topologyBuilder.topology.getRouters().put(name, new Router(name));
        return this;
    }

    public TopologyBuilder addRouters() {
        return topologyBuilder;
    }


}
