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

package io.patriot_framework.network.simulator.api.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Topology {
    HashMap<String, Router> routers = new HashMap<>();
    ArrayList<Network> networkTop;

    public Topology(HashMap<String, Router> routers, ArrayList<Network> networkTop) {
        this.routers = routers;
        this.networkTop = networkTop;
    }

    public Topology(ArrayList<Network> networkTop) {
        this.networkTop = networkTop;
    }

    public Topology(Integer networkCount) {
        this.networkTop = new ArrayList<>(networkCount);
    }

    public HashMap<String, Router> getRouters() {
        return routers;
    }

    public void setRouters(HashMap<String, Router> routers) {
        this.routers = routers;
    }

    public ArrayList<Network> getNetworkTop() {
        return networkTop;
    }

    public void setNetworkTop(ArrayList<Network> networkTop) {
        this.networkTop = networkTop;
    }
}
