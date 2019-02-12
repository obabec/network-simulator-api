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

import com.github.jgonian.ipmath.Ipv4;
import com.github.jgonian.ipmath.Ipv4Range;
import io.patriot_framework.network.simulator.api.api.iproute.RouteRestController;
import io.patriot_framework.network.simulator.api.model.devices.Device;
import io.patriot_framework.network.simulator.api.model.devices.application.JavaApplication;
import io.patriot_framework.network.simulator.api.model.devices.router.NetworkInterface;
import io.patriot_framework.network.simulator.api.control.Controller;
import io.patriot_framework.network.simulator.api.model.EnvironmentPart;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.router.Router;
import io.patriot_framework.network.simulator.api.model.network.Network;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import io.patriot_framework.network.simulator.api.model.routes.Route;
import io.patriot_framework.network_simulator.docker.control.DockerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manager is used for managing topology (deploying, destroying).
 */
public class Manager {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * Deploy all topology. Creates all virtual devices (routers), networks. Connect them as topology describes.
     * Calculate routes and set them to routes.
     * @param topology
     */
    public void deployTopology(Topology topology) {
        createNetworks(topology.getNetworks());
        createRouters(topology);
        connectNetworks(topology);
        updateRouters(topology);
        calcRoutes(topology);
        setRoutes(processRoutes(topology), topology);
    }

    /**
     * Calculates routers via Floyd-Warshall algo.
     * @param topology
     */
    private void calcRoutes(Topology topology) {
        ArrayList<TopologyNetwork> topologyNetworks = topology.getNetworks();
        LOGGER.info("Calculating network routes.");
        int size = topologyNetworks.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    if (i == k || i == j || j == k) {
                        continue;
                    }
                    int cost = topologyNetworks.get(i).getCalcRoutes().get(j).getCost()
                            + topologyNetworks.get(j).getCalcRoutes().get(k).getCost();
                    int actualCost = topologyNetworks.get(i).getCalcRoutes().get(k).getCost();

                    if (actualCost == (size + 1) || actualCost > cost) {
                        swapCalcRoutes(topologyNetworks, i, j, k);
                    }
                }
            }
        }
    }

    private void swapCalcRoutes(ArrayList<TopologyNetwork> topologyNetworks, int i, int j, int k) {
        NextHop nextHop = new NextHop(topologyNetworks.get(i).getCalcRoutes()
                .get(j).getNextHop().getRouter(),
                topologyNetworks.get(i).getCalcRoutes().get(j).getNextHop().getNetwork());

        CalcRoute c = new CalcRoute(nextHop, topologyNetworks.get(i).getCalcRoutes().get(j).getCost()
                + topologyNetworks.get(j).getCalcRoutes().get(k).getCost());
        topologyNetworks.get(i).getCalcRoutes().set(k, c);
    }


    /**
     * Parses calculated routes to actual route objects.
     * @param topology
     * @return HashMap with routers name as key and routes which has to be set to routers routing table.
     */
    private HashMap<String, ArrayList<Route>> processRoutes(Topology topology) {
        LOGGER.info("Processing routes to ipRoute2 format.");
        ArrayList<TopologyNetwork> calculatedTop = topology.getNetworks();
        int size = calculatedTop.size();
        HashMap<String, ArrayList<Route>> routes = new HashMap<>();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < calculatedTop.get(i).getCalcRoutes().size(); j++) {
                if (i == j || calculatedTop.get(i).getCalcRoutes().get(j) == null) continue;
                Router r = calculatedTop.get(i).getCalcRoutes().get(j).getNextHop().getRouter();
                Route route = completeRoute(calculatedTop, r, i, j);

                if (!routes.containsKey(r.getName())) {
                    routes.put(r.getName(), new ArrayList<>(Arrays.asList(route)));
                } else {
                    if (isProcessedRoute(routes.get(r.getName()), route)) {
                        continue;
                    }
                    routes.get(r.getName()).add(route);
                }
            }
        }
        return routes;
    }

    /**
     * Completes route object.
     * @param topologyNetworks networks
     * @param r router
     * @param i source network index
     * @param j destination network index
     * @return complete route object with all attributes
     */
    private Route completeRoute(ArrayList<TopologyNetwork> topologyNetworks, Router r, int i, int j) {
        Route route = new Route();

        route.setTargetRouter(r);

        route.setSource(topologyNetworks.get(i));
        route.setDest(topologyNetworks.get(j));

        Integer nextNetwork = topologyNetworks.get(i).getCalcRoutes().get(j).getNextHop().getNetwork();
        NetworkInterface target = findCorrectInterface(r, topologyNetworks.get(nextNetwork));
        route.setrNetworkInterface(target);
        return route;
    }


    /**
     * Finds correct network interface on router for target network.
     * @param source
     * @param topologyNetwork
     * @return network interface which is in target network
     */
    private NetworkInterface findCorrectInterface(Router source, TopologyNetwork topologyNetwork) {
        LOGGER.info("Finding correct interface for " + topologyNetwork.getName() + " on " + source.getName());
        Ipv4Range range = Ipv4Range.parse(topologyNetwork.getIPAddress() + "/" + topologyNetwork.getMask());

        for (int i = 0; i < source.getInterfaces().size(); i++) {
            if (range.contains(Ipv4.of(source.getInterfaces().get(i).getIp()))) {
                LOGGER.debug("Found correct interface: " + source.getInterfaces().get(i));
                return source.getInterfaces().get(i);
            }
        }
        return null;
    }

    /**
     * Checks if route is already present in list.
     * @param routes
     * @param route
     * @return true if route is present
     */
    private boolean isProcessedRoute(ArrayList<Route> routes, Route route) {
        for (Route r : routes) {
            if (r.getDest() == route.getDest() && r.getrNetworkInterface() == route.getrNetworkInterface()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sets routes to routing table via REST API on targeted routers.
     * @param processedRoutes
     * @param topology
     */
    public void setRoutes(HashMap<String, ArrayList<Route>> processedRoutes, Topology topology) {

        RouteRestController routeController = new RouteRestController();
        for (Map.Entry<String, ArrayList<Route>> entry: processedRoutes.entrySet()) {
            Router r = topology.findRouterByName(entry.getKey());
            LOGGER.info("Setting routes to routing table on " + r.getName());

            for (Route route : entry.getValue()) {
                if (route.getDest().getInternet()){
                    LOGGER.debug("Setting default route");
                    routeController.addDefaultGW(route, r.getIPAddress(), r.getMngPort());
                } else {
                    routeController.addRoute(route, r.getIPAddress(), r.getMngPort());
                }
            }
        }
    }



    /**
     * Method updates interfaces of routers. Usually have to be called after
     * routers are connected to networks.
     * @param topology
     */
    //TODO: Needs to complete rest controllers first!
    private void updateRouters(Topology topology) {
        RouteRestController restController = new RouteRestController();
        LOGGER.info("Requesting information about routers interfaces.");
        for (Router r : topology.getRouters()) {
            r.setNetworkInterfaces(restController.getInterfaces(r.getIPAddress(), r.getMngPort()));
        }
    }

    /**
     * Connects networks as topology describes.
     * @param topology
     */
    //TODO: What will happen if network is created by different creator than router?
    private void connectNetworks(Topology topology) {
        LOGGER.info("Connecting networks.");
        HashMap<String, List<TopologyNetwork>> directConnections = filterConnected(topology);
        for (Map.Entry<String, List<TopologyNetwork>> entry : directConnections.entrySet()) {
            Router r = topology.findRouterByName(entry.getKey());
            Controller controller = findController(r);
            for (TopologyNetwork network : entry.getValue()) {
                LOGGER.debug("Connecting router " + r.getName() + "with " + network.getName());
                controller.connectDeviceToNetwork(r, network);
            }
        }
    }



    /**
     * Method provides filtering of networks which have to be physically connected
     * to other network via router, as it's described in topology.
     * @param topology
     * @return Hash map with router`s name as key and networks with which router has to be connected
     */
    private HashMap<String, List<TopologyNetwork>> filterConnected(Topology topology) {
        LOGGER.info("Filtering direct connections.");
        HashMap<String, List<TopologyNetwork>> connections = new HashMap<>();

        int topologySize = topology.getNetworks().size();
        ArrayList<TopologyNetwork> networks = topology.getNetworks();

        for (int i = 0; i < topologySize; i++) {
            TopologyNetwork network = networks.get(i);
            for (int y = 0; y < topologySize; y++) {
                CalcRoute calcRoute = network.getCalcRoutes().get(y);
                if (calcRoute.getCost() == 1) {
                    String rName = calcRoute.getNextHop().getRouter().getName();
                    TopologyNetwork dNetwork = networks.get(calcRoute.getNextHop().getNetwork());
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
    private void connectionExists(String rName, List<TopologyNetwork> networks, HashMap<String, List<TopologyNetwork>> connections) {
        LOGGER.debug("Controlling if direct connection is already stored.");
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

    /**
     * Creates networks.
     * @param networkList
     */
    private void createNetworks(ArrayList<TopologyNetwork> networkList) {
        for (TopologyNetwork network : networkList) {
            LOGGER.debug("Creating network: " + network.getName());
            findController(network).createNetwork(network);
        }
    }

    /**
     * Creates routers and updates mng IP.
     * @param topology
     */
    private void createRouters(Topology topology) {
        for (Router router : topology.getRouters()) {
            LOGGER.debug("Creating router: " + router.getName());
            findController(router).deployDevice(router, topology.getRoutersTag());
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

    /**
     * Clean all routers, networks in topology (stop and delete).
     * @param topology
     */
    public void cleanUp(Topology topology) {

        Controller controller;
        for (Router r : topology.getRouters()) {
            controller = findController(r);
            controller.destroyDevice(r);
        }
        for (Network n : topology.getNetworks()) {
            controller = findController(n);
            controller.destroyNetwork(n);
        }
    }

    //TODO: This is not 100% legit, application should maybe be just one class not an interface.
    public Device deployApplicationFromImage(String name, String tag, List<Network> connectedNetworks) {
        JavaApplication javaApplication = new JavaApplication();
        Controller controller = findController(javaApplication);
        controller.deployDevice(javaApplication, tag);
        controller.connectDeviceToNetwork(javaApplication, connectedNetworks);
        return javaApplication;
    }

}