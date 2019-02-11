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

package io.patriot_framework.network.simulator.api.manager;

import io.patriot_framework.network.simulator.api.control.Controller;
import io.patriot_framework.network.simulator.api.model.EnvironmentPart;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.Device;
import io.patriot_framework.network.simulator.api.model.devices.router.Router;
import io.patriot_framework.network.simulator.api.model.network.NImpl;
import io.patriot_framework.network.simulator.api.model.network.Network;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network_simulator.docker.control.DockerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Manager is used for managing topology (deploying, destroying).
 */
public class Manager {

    //Controller controller;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void deployTopology(Topology topology) {

    }

    /**
     * Method deploys (create, start) routers.
     * @param routers
     */
    private void deployRouters(List<Router> routers) {
        Controller controller;
        for (Router router : routers) {
            controller = findController(router);
            controller.deployRouter(router);
        }
    }

    /**
     * Method updates interfaces of routers. Usually have to be called after
     * routers are connected to networks.
     * @param topology
     */
    //TODO: Needs to complete rest controllers first!
    private void updateRouters(Topology topology) {
        Controller controller;
    }

    /**
     * Connects networks as topology describes.
     * @param topology
     */
    //TODO: What will happen if network is created by different creator than router?
    private void connectNetworks(Topology topology) {
        HashMap<String, List<Network>> directConnections = filterConnected(topology);
        for (Map.Entry<String, List<Network>> entry : directConnections.entrySet()) {
            Router r = findRouterByName(entry.getKey(), topology.getRouters());
            Controller controller = findController(r);
            for (Network network : entry.getValue()) {
                controller.connectDeviceToNetwork(r, network);
            }
        }
    }

    /**
     * Finds router in list by name.
     * @param name
     * @param routers
     * @return
     */
    private Router findRouterByName(String name, List<Router> routers) {
        for (Router r : routers) {
            if (r.getName().equals(name)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Method provides filtering of networks which have to be physically connected
     * to other network via router, as it's described in topology.
     * @param topology
     * @return Hash map with router`s name as key and networks with which router has to be connected
     */
    private HashMap<String, List<Network>> filterConnected(Topology topology) {
        HashMap<String, List<Network>> connections = new HashMap<>();

        int topologySize = topology.getNetworks().size();
        ArrayList<NImpl> networks = topology.getNetworks();

        for (int i = 0; i < topologySize; i++) {
            NImpl network = networks.get(i);
            for (int y = 0; y < topologySize; y++) {
                CalcRoute calcRoute = network.getCalcRoutes().get(y);
                if (calcRoute.getCost() == 1) {
                    String rName = calcRoute.getNextHop().getRouter().getName();
                    Network dNetwork = networks.get(calcRoute.getNextHop().getNetwork());
                    connectionExists(rName, Arrays.asList(network, dNetwork), connections);
                }
            }
        }
        return connections;
    }

    /**
     * Method checks if target networks are stored in connections array. If not, they are inserted into connections
     * map, if router name is already stored in map, method checks if networks are stored too. If they are not
     * present, value is updated.
     * @param rName
     * @param networks
     * @param connections
     */
    private void connectionExists(String rName, List<Network> networks, HashMap<String, List<Network>> connections) {
        if (connections.keySet().contains(rName)) {
            for (int i = 0; i < 2; i++) {
                if (!connections.get(rName).contains(networks.get(i))) {
                    connections.get(rName).add(networks.get(i));
                }
            }
        } else {
            connections.put(rName, networks);
        }
    }

    private void createNetworks(List<Network> networkList) {
        for (Network network : networkList) {
            findController(network).createNetwork(network);
        }
    }

    private void createRouters(List<Router> routers) {
        for (Router router : routers) {
            findController(router).deployRouter(router);
        }
    }

    /**
     * Finds controller by string identifier. For 'Docker' will return Docker implementation of
     * controller.
     * @param environmentPart part of creating environment like network, router, ...
     * @return
     */
    //TODO: Maybe controller would be good as singleton...?
    private Controller findController(EnvironmentPart environmentPart) {
        if (environmentPart.getCreator() == "Docker") {
            return new DockerController();
        } else {
            LOGGER.error("Cannot find matching creator!");
            return null;
        }
    }

}
