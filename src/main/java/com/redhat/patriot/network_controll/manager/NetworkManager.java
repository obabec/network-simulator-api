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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Network manager.
 */
public class NetworkManager {
    /**
     * Calc routes array list.
     *
     * @param topology the topology
     * @return the array list
     */
    public void calcRoutes(ArrayList<Network> topology) {

        int size = topology.size();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {

                    if (i == k || i == j || j == k) continue;

                    int cost = topology.get(i).getCalcRoute().get(j).getCost() + topology.get(j).getCalcRoute().get(k).getCost();

                    if (topology.get(i).getCalcRoute().get(k).getCost() == size || topology.get(i).getCalcRoute().get(k).getCost() > cost) {

                        NextHop nextHop = new NextHop(topology.get(i).getCalcRoute().get(j).getNextHop().getRouter(), j);
                        CalcRoute c = new CalcRoute(nextHop, cost);
                        topology.get(i).getCalcRoute().set(k, c);
                    }

                }
            }
        }
    }


    /**
     * Process routes hash map.
     *
     * @param calculatedRoutes the calculated routes
     * @return the hash map
     */
    public HashMap<String, ArrayList<Route>> processRoutes(ArrayList<Network> calculatedRoutes) {
        int size = calculatedRoutes.size();
        HashMap<String, ArrayList<Route>> routes = new HashMap<>();
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                if (i == j) continue;

                Route route = new Route();
                      route.setSource(calculatedRoutes.get(1));
                Router r = calculatedRoutes.get(i).getCalcRoute().get(j).getNextHop().getRouter();
                route.setTargetRouter(r);
                route.setDest(calculatedRoutes.get(j));


                Integer destNetwork = calculatedRoutes.get(i).getCalcRoute().get(j).getNextHop().getNetwork();
                NetworkInterface target = findCorrectInterface(r, calculatedRoutes.get(destNetwork));
                route.setrNetworkInterface(target);

                if (!routes.containsKey(r.getName())) {
                    routes.put(r.getName(), new ArrayList<>(Arrays.asList(route)));
                } else {
                    routes.get(r.getName()).add(route);
                }
            }
        }
        return routes;
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
     * Sets routes.
     *
     * @param processedRoutes the processed routes
     */
    public void setRoutes(HashMap<Router, ArrayList<Route>> processedRoutes) {
        RouteController routeController;
        List<String> routes = new ArrayList<>();
        for (Map.Entry<Router, ArrayList<Route>> entry: processedRoutes.entrySet()) {

            Router r = entry.getValue().get(0).getTargetRouter();
            routeController = new RouteController(r.getMngIp(), r.getMngPort());

            for (Route route : entry.getValue()) {
                //routeController.addRoute(route);
                routes.add(route.toPath());
            }
        }
    }

}
