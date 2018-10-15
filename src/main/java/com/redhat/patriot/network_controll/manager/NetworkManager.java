package com.redhat.patriot.network_controll.manager;


import com.github.jgonian.ipmath.Ipv4;
import com.github.jgonian.ipmath.Ipv4Range;
import com.redhat.patriot.network_controll.api.iproute.NetworkInterface;
import com.redhat.patriot.network_controll.api.iproute.RouteController;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;
import com.redhat.patriot.network_controll.model.routes.CalcRoute;
import com.redhat.patriot.network_controll.model.routes.NextHop;
import com.redhat.patriot.network_controll.model.routes.Route;
import com.redhat.patriot.network_simulator.example.container.DockerContainer;
import com.redhat.patriot.network_simulator.example.image.docker.DockerImage;
import com.redhat.patriot.network_simulator.example.manager.DockerManager;
import com.redhat.patriot.network_simulator.example.network.DockerNetwork;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * The type Network manager.
 */
public class NetworkManager {

    /**
     * Physical (docker) connection of devices.
     *
     * @param topology the topology
     * @param routers  the routers
     * @return the hash map
     */
    public HashMap<String, Router> connect(ArrayList<Network> topology, HashMap<String, Router> routers) {
        routers = filterDirected(topology, routers);
        DockerManager dockerManager = new DockerManager();
        DockerImage dockerImage = new DockerImage(dockerManager);
        HashMap<String, DockerNetwork> dockerNetworks = createNetworks(topology, dockerManager);
        HashMap<String, DockerContainer> dockerRouters = new HashMap<>();
        RouteController routeController;
        try {
            dockerImage.buildRouterImage(new HashSet<>(Arrays.asList("router")));

            for (Map.Entry<String, Router> routerEntry : routers.entrySet()) {

                 dockerRouters.put(routerEntry.getValue().getName(), (DockerContainer) dockerManager
                        .createContainer(routerEntry.getKey(), "router"));
                 dockerManager.startContainer(dockerRouters.get(routerEntry.getValue().getName()));

                 routerEntry.getValue().setMngIp(dockerManager.findIpAddress(dockerRouters.get(routerEntry.getKey())));

                routeController = new RouteController(routerEntry.getValue().getMngIp(), 5000);

                for (Network network : routerEntry.getValue().getConnectedNetworks()) {
                    if (!network.getInternet()) {
                        dockerManager.connectContainerToNetwork(dockerRouters.get(routerEntry.getKey()),
                                dockerNetworks.get(network.getName()));
                    }
                }
                routerEntry.getValue().setNetworkInterfaces(routeController.getInterfaces());
                dockerManager.delDefaultGateway(dockerRouters.get(routerEntry.getValue().getName()));
                String s ="";
            }

            initInternetNetworkAddress(topology, dockerRouters.values().iterator().next());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return routers;
    }

    private void initInternetNetworkAddress(ArrayList<Network> topology, DockerContainer dockerContainer) {
        for (Network n : topology) {
            if (n.getInternet()) {
                n.setIpAddress(dockerContainer.getGatewayNetworkIp());
                n.setMask(dockerContainer.getGatewayNetworkMask());
            }

        }
    }

    private HashMap<String, DockerNetwork> createNetworks(ArrayList<Network> topology, DockerManager dockerManager) {
        HashMap<String, DockerNetwork> dockerNetworks = new HashMap<>();
        for (Network network : topology){
            if (network.getInternet()) continue;
            dockerNetworks.put(network.getName(),
                    (DockerNetwork) dockerManager.createNetwork(network.getName(),
                            network.getIpWithMask()));
        }
        return dockerNetworks;
    }

    private HashMap<String, Router> filterDirected(ArrayList<Network> topology, HashMap<String, Router> routers) {

        for (int i = 0; i < topology.size(); i++) {
            for (CalcRoute c : topology.get(i).getCalcRoutes()) {
                if (c.getCost() == null) {
                    continue;
                }
                if (c.getCost() == 1) {
                    String rName = c.getNextHop().getRouter().getName();

                    if (!routers.get(rName).getConnectedNetworks().contains(topology.get(i))) {

                        routers.get(rName).getConnectedNetworks().add(topology.get(i));

                    } else if (!routers.get(rName).getConnectedNetworks()
                            .contains(topology.get(c.getNextHop().getNetwork()))) {

                        routers.get(rName).getConnectedNetworks().add(topology.get(c.getNextHop().getNetwork()));
                    }
                }
            }
        }
        return routers;
    }

    /**
     * Calculate shortest path to destination network,
     * described in topology with n + 1 cost.
     *
     * @param topology the topology
     */
    public void calcRoutes(ArrayList<Network> topology) {

        int size = topology.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {

                    if (i == k || i == j || j == k) continue;

                    int cost = topology.get(i).getCalcRoutes().get(j).getCost()
                            + topology.get(j).getCalcRoutes().get(k).getCost();

                    if (topology.get(i).getCalcRoutes().get(k).getCost() == (size + 1) ||
                            topology.get(i).getCalcRoutes().get(k).getCost() > cost) {

                        NextHop nextHop = new NextHop(topology.get(i).getCalcRoutes()
                                .get(j).getNextHop().getRouter(),
                                topology.get(i).getCalcRoutes().get(j).getNextHop().getNetwork());

                        CalcRoute c = new CalcRoute(nextHop, topology.get(i).getCalcRoutes().get(j).getCost()
                                + topology.get(j).getCalcRoutes().get(k).getCost());
                        topology.get(i).getCalcRoutes().set(k, c);
                    }

                }
            }
        }
    }

    /**
     * Convert calculated routes to Router' s routing table format.
     *
     * @param calculatedRoutes the calculated routes
     * @return Hash map of routers and routes which have to be set in router' s routing tables.
     */
    public HashMap<String, ArrayList<Route>> processRoutes(ArrayList<Network> calculatedRoutes) {
        int size = calculatedRoutes.size();
        HashMap<String, ArrayList<Route>> routes = new HashMap<>();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (i == j) continue;
                Route route = new Route();
                Router r = calculatedRoutes.get(i).getCalcRoutes().get(j).getNextHop().getRouter();
                route.setTargetRouter(r);

                route.setSource(calculatedRoutes.get(i));
                route.setDest(calculatedRoutes.get(j));

                Integer nextNetwork = calculatedRoutes.get(i).getCalcRoutes().get(j).getNextHop().getNetwork();
                NetworkInterface target = findCorrectInterface(r, calculatedRoutes.get(nextNetwork));
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

        Ipv4Range range = Ipv4Range.parse(network.getIpAddress() + "/" + network.getMask());

        for (int i = 0; i < source.getNetworkInterfaces().size(); i++) {
            if (range.contains(Ipv4.of(source.getNetworkInterfaces().get(i).getIp()))) {
                return source.getNetworkInterfaces().get(i);
            }
        }
        return null;
    }


    /**
     * Set routes to Router' s routing table using Route Controller.
     *
     * @param processedRoutes the processed routes
     */
    public void setRoutes(HashMap<String, ArrayList<Route>> processedRoutes, HashMap<String, Router> routers) {
        RouteController routeController;
        for (Map.Entry<String, ArrayList<Route>> entry: processedRoutes.entrySet()) {

            Router r = routers.get(entry.getKey());
            routeController = new RouteController(r.getMngIp(), r.getMngPort());

            for (Route route : entry.getValue()) {
                if (route.getDest().getInternet()){
                    routeController.addDefaultGW(route);
                } else {
                    routeController.addRoute(route);
                }

            }
        }
    }

}
