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

import io.patriot_framework.network.simulator.api.api.iproute.NetworkInterface;
import io.patriot_framework.network.simulator.api.api.iproute.RouteController;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import io.patriot_framework.network.simulator.api.model.routes.Route;
import io.patriot_framework.network_simulator.docker.container.DockerContainer;
import io.patriot_framework.network_simulator.docker.image.docker.DockerImage;
import io.patriot_framework.network_simulator.docker.manager.DockerManager;
import io.patriot_framework.network_simulator.docker.network.DockerNetwork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Network manager is providing basic topology requirement methods. Only via network manager is collaborating
 * with network-simulator api.
 */
public class NetworkManager {

    private DockerManager dockerManager;
    private Logger logger;

    public NetworkManager() {
        this.dockerManager = new DockerManager();
        this.logger = LoggerFactory.getLogger(NetworkManager.class);
    }

    /**
     * Function provides creation, start of all networks and routers,
     * connection of devices which are described in topology with cost = 1.
     *
     * @param topology the topology
     * @return the hash map
     */
    public HashMap<String, Router> connect(Topology topology) {


        topology.setRouters(filterDirected(topology));
        DockerImage dockerImage = new DockerImage(dockerManager);
        HashMap<String, DockerNetwork> dockerNetworks = createNetworks(topology.getNetworks());
        HashMap<String, DockerContainer> dockerRouters = new HashMap<>();
        RouteController routeController;
        try {
            dockerImage.buildRouterImage(new HashSet<>(Arrays.asList("router")));

            for (Map.Entry<String, Router> routerEntry : topology.getRouters().entrySet()) {

                 dockerRouters.put(routerEntry.getValue().getName(), (DockerContainer) dockerManager
                        .createContainer(routerEntry.getKey(), "router"));
                 dockerManager.startContainer(dockerRouters.get(routerEntry.getValue().getName()));

                 routerEntry.getValue().setMngIp(dockerManager.findIpAddress(dockerRouters.get(routerEntry.getKey())));

                 routeController = new RouteController(routerEntry.getValue().getMngIp(),
                        routerEntry.getValue().getMngPort());

                for (Network network : routerEntry.getValue().getConnectedNetworks()) {
                    if (!network.getInternet()) {
                        dockerManager.connectContainerToNetwork(dockerRouters.get(routerEntry.getKey()),
                                dockerNetworks.get(network.getName()));
                    }
                }
                routerEntry.getValue().setNetworkInterfaces(routeController.getInterfaces());
                dockerManager.delDefaultGateway(dockerRouters.get(routerEntry.getValue().getName()));
            }
            initInternetNetworkAddress(topology.getNetworks(), dockerRouters.values().iterator().next());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topology.getRouters();
    }

    private void initInternetNetworkAddress(ArrayList<Network> topology, DockerContainer dockerContainer) {
        for (Network n : topology) {
            if (n.getInternet()) {
                n.setIpAddress(dockerContainer.getGatewayNetworkIp());
                n.setMask(dockerContainer.getGatewayNetworkMask());
            }
        }
    }

    private HashMap<String, DockerNetwork> createNetworks(ArrayList<Network> topology) {
        HashMap<String, DockerNetwork> dockerNetworks = new HashMap<>();
        for (Network network : topology){
            if (network.getInternet()) {
                continue;
            }
            dockerNetworks.put(
                    network.getName(),
                    (DockerNetwork) dockerManager.createNetwork(network.getName(), network.getIpWithMask()));
            logger.info("Created network with id: " + dockerNetworks.get(network.getName()).getId());
        }
        return dockerNetworks;
    }

    private HashMap<String, Router> filterDirected(Topology topology) {
        ArrayList<Network> networks = topology.getNetworks();
        HashMap<String, Router> routers = topology.getRouters();
        for (int i = 0; i < networks.size(); i++) {
            logger.info("Filtering connected networks with " + networks.get(i).getName() + " network.");
            for (CalcRoute c : networks.get(i).getCalcRoutes()) {
                if (c.getCost() == null) {
                    continue;
                }
                if (c.getCost() == 1) {
                    String rName = c.getNextHop().getRouter().getName();
                    if (!routers.get(rName).getConnectedNetworks().contains(networks.get(i))) {
                        routers.get(rName).getConnectedNetworks().add(networks.get(i));
                    } else if (!routers.get(rName).getConnectedNetworks()
                            .contains(networks.get(c.getNextHop().getNetwork()))) {
                        routers.get(rName).getConnectedNetworks().add(networks.get(c.getNextHop().getNetwork()));
                    }
                }
            }
        }
        return routers;
    }

    /**
     * Calculate shortest path to destination network. Route which need' s to be calculated must be
     * described in topology with n + 1 cost.
     *
     * n = number of networks
     * @param topology network topology
     */
    public void calcRoutes(Topology topology) {
        ArrayList<Network> networks = topology.getNetworks();
        logger.info("Calculating network routes.");
        int size = networks.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {

                    if (i == k || i == j || j == k) {
                        continue;
                    }
                    int cost = networks.get(i).getCalcRoutes().get(j).getCost()
                            + networks.get(j).getCalcRoutes().get(k).getCost();

                    int actualCost = networks.get(i).getCalcRoutes().get(k).getCost();

                    if (actualCost == (size + 1) || actualCost > cost) {
                        NextHop nextHop = new NextHop(networks.get(i).getCalcRoutes()
                                .get(j).getNextHop().getRouter(),
                                networks.get(i).getCalcRoutes().get(j).getNextHop().getNetwork());

                        CalcRoute c = new CalcRoute(nextHop, networks.get(i).getCalcRoutes().get(j).getCost()
                                + networks.get(j).getCalcRoutes().get(k).getCost());
                        networks.get(i).getCalcRoutes().set(k, c);
                    }
                }
            }
        }
    }

    /**
     * Convert calculated routes to Router' s routing table format.
     *
     * @param topology Calculated network topology
     * @return Hash map of routers and routes which have to be set in router' s routing tables.
     */
    public HashMap<String, ArrayList<Route>> processRoutes(Topology topology) {
        logger.info("Processing routes to ipRoute2 format.");
        ArrayList<Network> calculatedTop = topology.getNetworks();
        int size = calculatedTop.size();
        HashMap<String, ArrayList<Route>> routes = new HashMap<>();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < calculatedTop.get(i).getCalcRoutes().size(); j++) {
                if (i == j || calculatedTop.get(i).getCalcRoutes().get(j) == null) continue;
                Route route = new Route();
                Router r = calculatedTop.get(i).getCalcRoutes().get(j).getNextHop().getRouter();
                route.setTargetRouter(r);

                route.setSource(calculatedTop.get(i));
                route.setDest(calculatedTop.get(j));

                Integer nextNetwork = calculatedTop.get(i).getCalcRoutes().get(j).getNextHop().getNetwork();
                NetworkInterface target = findCorrectInterface(r, calculatedTop.get(nextNetwork));
                route.setrNetworkInterface(target);

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

    private boolean isProcessedRoute(ArrayList<Route> routes, Route route) {
        for (Route r : routes) {
            if (r.getDest() == route.getDest() && r.getrNetworkInterface() == route.getrNetworkInterface()) {
                return true;
            }
        }
        return false;
    }


    private NetworkInterface findCorrectInterface(Router source, Network network) {
        logger.info("Finding correct interface for " + network.getName() + " on " + source.getName());
        Ipv4Range range = Ipv4Range.parse(network.getIpAddress() + "/" + network.getMask());

        for (int i = 0; i < source.getNetworkInterfaces().size(); i++) {
            if (range.contains(Ipv4.of(source.getNetworkInterfaces().get(i).getIp()))) {
                logger.debug("Found correct interface: " + source.getNetworkInterfaces().get(i));
                return source.getNetworkInterfaces().get(i);
            }
        }
        return null;
    }


    /**
     * Add records to existing routing table in routers.
     *
     * @param processedRoutes the processed routes
     * @param routers routers in topology
     */
    public void setRoutes(HashMap<String, ArrayList<Route>> processedRoutes, HashMap<String, Router> routers) {

        RouteController routeController;
        for (Map.Entry<String, ArrayList<Route>> entry: processedRoutes.entrySet()) {

            Router r = routers.get(entry.getKey());
            logger.info("Setting routes to routing table on " + r.getName());
            routeController = new RouteController(r.getMngIp(), r.getMngPort());

            for (Route route : entry.getValue()) {
                if (route.getDest().getInternet()){
                    logger.debug("Setting default route");
                    routeController.addDefaultGW(route);
                } else {
                    routeController.addRoute(route);
                }
            }
        }
    }
}
