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


/**
 * Wrapper representing full network topology.
 */
public class Topology {
    /**
     * Routers located in topology.
     */
    HashMap<String, Router> routers = new HashMap<>();
    /**
     * Networks in topology.
     */
    ArrayList<Network> networks;

    /**
     * Instantiates a new Topology.
     *
     * @param routers    the routers
     * @param networks the network top
     */
    public Topology(HashMap<String, Router> routers, ArrayList<Network> networks) {
        this.routers = routers;
        this.networks = networks;
    }

    /**
     * Instantiates a new Topology.
     *
     * @param networks the network top
     */
    public Topology(ArrayList<Network> networks) {
        this.networks = networks;
    }

    /**
     * Instantiates a new Topology.
     *
     * @param networkCount the network count
     */
    public Topology(Integer networkCount) {
        this.networks = new ArrayList<>(networkCount);
    }

    /**
     * Gets routers.
     *
     * @return the routers
     */
    public HashMap<String, Router> getRouters() {
        return routers;
    }

    /**
     * Sets routers.
     *
     * @param routers the routers
     */
    public void setRouters(HashMap<String, Router> routers) {
        this.routers = routers;
    }

    /**
     * Gets network top.
     *
     * @return the network top
     */
    public ArrayList<Network> getNetworks() {
        return networks;
    }

    /**
     * Sets network top.
     *
     * @param networks the network top
     */
    public void setNetworks(ArrayList<Network> networks) {
        this.networks = networks;
    }
}
