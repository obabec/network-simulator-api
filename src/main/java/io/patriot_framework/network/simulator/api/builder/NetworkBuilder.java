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

import io.patriot_framework.network.simulator.api.CalculatedRouteList;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;

public class NetworkBuilder {

    private String ipAddress;
    private String name;
    private Integer mask;
    private Boolean internet = false;
    public CalculatedRouteList<CalcRoute> calcRoutes = new CalculatedRouteList<>();
    TopologyBuilder t;

    public NetworkBuilder(TopologyBuilder t, String name) {
        this.t = t;
        this.name = name;
    }

    public NetworkBuilder(String name){
        this.name = name;
    }

    public NetworkBuilder withIP(String ip) {
        this.ipAddress = ip;
        return this;
    }
    public NetworkBuilder withMask(Integer mask) {
        this.mask = mask;
        return this;
    }
    public NetworkBuilder withInternet(Boolean internet) {
        this.internet = internet;
        return this;
    }
    public NetworkBuilder withCalcRoutes(CalculatedRouteList<CalcRoute> calcRoutes) {
        this.calcRoutes = calcRoutes;
        return this;
    }
    public CalcRouteBuilder withCalcRoute(){
        return new CalcRouteBuilder(this);
    }


    public Network build() {

        Network n = new Network();
        n.setCalcRoutes(calcRoutes);
        n.setInternet(internet);
        n.setIpAddress(ipAddress);
        n.setMask(mask);
        n.setName(name);
        return n;
    }
    public TopologyBuilder create() {
        t.topology.add(build());
        return t;
    }

}
