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

package io.patriot_framework.network.simulator.api;

import io.patriot_framework.network.simulator.api.api.iproute.NetworkInterface;
import io.patriot_framework.network.simulator.api.api.iproute.RouteController;
import io.patriot_framework.network.simulator.api.manager.NetworkManager;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import io.patriot_framework.network.simulator.api.model.routes.Route;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTreatmentTest {

    @Test
    public void testRouteTreatmentTest() {
        NetworkManager nManager = new NetworkManager();
        ArrayList<Network> topology = new ArrayList<>();
        Router r = new Router("TestRouter");
        Route route = createSimpleTopology(topology, false, r);
        HashMap<String, ArrayList<Route>> routes = nManager.processRoutes(topology);
        assertTrue(routes.get(r.getName()).get(0).toPath().equals(route.toPath()));

    }

    private Route parseRoute(Network source, Network dest, Router targetRouter, NetworkInterface targetInterface) {
        Route route = new Route();
        route.setrNetworkInterface(targetInterface);
        route.setSource(source);
        route.setDest(dest);
        route.setTargetRouter(targetRouter);
        return route;
    }

    @Test
    public void duplicateRouteTreatmentTest() {
        NetworkManager nManager = new NetworkManager();
        ArrayList<Network> topology = new ArrayList<>();
        Router r = new Router("TestRouter");
        Route route = createSimpleTopology(topology, true, r);
        createDuplicateNetwork(topology, r);

        HashMap<String, ArrayList<Route>> routes = nManager.processRoutes(topology);
        assertEquals(1, routes.size());
        assertTrue(routes.get(r.getName()).get(0).toPath().equals(route.toPath()));

    }

    private Route createSimpleTopology(ArrayList<Network> topology, Boolean duplicate, Router r) {
        List<NetworkInterface> routerInterfaces = new ArrayList<>();
        routerInterfaces.add(new NetworkInterface("eth0", "192.168.0.2", 24));
        r.setNetworkInterfaces(routerInterfaces);

        Network n1 = new Network();
        n1.setName("TestNetwork1");
        n1.setIpAddress("172.16.0.0");
        n1.setMask(16);

        Network n2 = new Network();
        n2.setName("TestNetwork2");
        n2.setIpAddress("192.168.0.0");
        n2.setMask(24);

        n1.setCalcRoutes(createCalculatedRouteList(r, 0, 1));

        topology.add(n1);
        topology.add(n2);
        return parseRoute(n1, n2, r, routerInterfaces.get(0));
    }

    private CalculatedRouteList createCalculatedRouteList(Router r, Integer sourceNetwork, Integer destNetwork) {
        CalcRoute calcRoute = new CalcRoute(new NextHop(r, destNetwork), 1);
        CalculatedRouteList calculatedRouteList = new CalculatedRouteList();
        calculatedRouteList.add(destNetwork, calcRoute);
        calculatedRouteList.add(sourceNetwork, new CalcRoute(new NextHop(null, sourceNetwork), null));
        return calculatedRouteList;
    }

    private void createDuplicateNetwork(ArrayList<Network> topology, Router r) {
        Network n3 = new Network();
        n3.setName("TestNetwork3");
        n3.setIpAddress("172.16.0.0");
        n3.setMask(16);

        n3.setCalcRoutes(createCalculatedRouteList(r, 2, 1));
        topology.add(n3);
    }


    @Test
    public void setRoutesTest() {

        HashMap<String, Router> routers = new HashMap<>();
        routers.put("R1", new Router("R1"));
        routers.put("R2", new Router("R2"));

        ArrayList<Network> topology = prepareComplicatedTopology(routers);

        NetworkManager networkManager = new NetworkManager();
        routers = networkManager.connect(topology, routers);
        networkManager.calcRoutes(topology);
        HashMap<String, ArrayList<Route>> hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap, routers);

        for (Router router : routers.values()) {
            RouteController routeController = new RouteController(router.getMngIp(), router.getMngPort());
            List<Route> routes = routeController.getRoutes();
            for (Route r : hashMap.get(router.getName())) {
                assertTrue(containsRoute(routes, r));
            }
        }
        CleanUtils cleanUtils = new CleanUtils();
        cleanUtils.cleanUp(topology, routers);
    }

    private Boolean containsRoute(List<Route> routes, Route route) {
        for (Route r : routes) {
            if (r.getDest().getIpAddress().equals(route.getDest().getIpAddress()) &&
                    r.getrNetworkInterface().getIp().equals(route.getrNetworkInterface().getIp())) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Network> prepareComplicatedTopology(HashMap<String, Router> routers) {
        ArrayList<Network> topology = new ArrayList<>();

        Network n1 = new Network();
        n1.setName("TestNetwork1");
        n1.setIpAddress("192.168.1.0");
        n1.setMask(28);

        Network n2 = new Network();
        n2.setName("TestNetwork2");
        n2.setIpAddress("192.168.1.16");
        n2.setMask(28);

        Network n3 = new Network();
        n3.setName("TestNetwork3");
        n3.setIpAddress("192.168.1.32");
        n3.setMask(28);
        topology.addAll(Arrays.asList(n1, n2, n3));

        prepareOnStartTopology(topology, routers);
        return topology;
    }

    private void prepareOnStartTopology(ArrayList<Network> topology, HashMap<String, Router> routers) {
        Integer routNeedCalc = topology.size() + 1;
        Network n1 = topology.get(0);
        Network n2 = topology.get(1);
        Network n3 = topology.get(2);

        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), null));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 1), 1));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), routNeedCalc));


        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 0), 1));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), null));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 2), 1));


        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 1), 1));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), null));

    }


}
