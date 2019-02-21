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

import io.patriot_framework.network.simulator.api.api.iproute.RouteRestController;
import io.patriot_framework.network.simulator.api.manager.NetworkManager;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.router.NetworkInterface;
import io.patriot_framework.network.simulator.api.model.devices.router.Router;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import io.patriot_framework.network.simulator.api.model.routes.Route;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
public class RouteTreatmentTest {

 /*   @Test
    public void testRouteTreatmentTest() {
        NetworkManager nManager = new NetworkManager("patriotRouter");
        ArrayList<TopologyNetwork> topologyNetworks = new ArrayList<>();
        Topology topology = new Topology(topologyNetworks);
        Router r = new Router("TestRouter");
        Route route = createSimpleTopology(topologyNetworks, false, r);
        HashMap<String, ArrayList<Route>> routes = nManager.processRoutes(topology);
        assertTrue(routes.get(r.getName()).get(0).toAPIFormat().equals(route.toAPIFormat()));

    }

    private Route parseRoute(TopologyNetwork source, TopologyNetwork dest, Router targetRouter, NetworkInterface targetInterface) {
        Route route = new Route();
        route.setrNetworkInterface(targetInterface);
        route.setSource(source);
        route.setDest(dest);
        route.setTargetRouter(targetRouter);
        return route;
    }

    @Test
    public void duplicateRouteTreatmentTest() {
        NetworkManager nManager = new NetworkManager("patriotRouter");
        ArrayList<TopologyNetwork> topologyNetworks = new ArrayList<>();
        HashMap<String, Router> routers = new HashMap<>();
        routers.put("TestRouter", new Router("TestRouter"));
        Topology topology = new Topology(routers, topologyNetworks);
        Route route = createSimpleTopology(topology.getNetworkImpls(), true, topology.getRouters().get("TestRouter"));
        createDuplicateNetwork(topology.getNetworkImpls() ,topology.getRouters().get("TestRouter"));

        HashMap<String, ArrayList<Route>> routes = nManager.processRoutes(topology);
        assertEquals(1, routes.size());
        assertTrue(routes.get(topology.getRouters().get("TestRouter").getName()).get(0).toAPIFormat().equals(route.toAPIFormat()));

    }

    private Route createSimpleTopology(ArrayList<TopologyNetwork> topology, Boolean duplicate, Router r) {
        List<NetworkInterface> routerInterfaces = new ArrayList<>();
        routerInterfaces.add(new NetworkInterface("eth0", "192.168.0.2", 24));
        r.setNetworkInterfaces(routerInterfaces);

        TopologyNetwork n1 = new TopologyNetwork();
        n1.setName("TestNetwork1");
        n1.setIpAddress("172.16.0.0");
        n1.setMask(16);

        TopologyNetwork n2 = new TopologyNetwork();
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

    private void createDuplicateNetwork(ArrayList<TopologyNetwork> topology, Router r) {
        TopologyNetwork n3 = new TopologyNetwork();
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

        ArrayList<TopologyNetwork> topologyNetworks = prepareComplicatedTopology(routers);
        Topology topology = new Topology(routers, topologyNetworks);
        NetworkManager networkManager = new NetworkManager("patriotRouter");
        routers = networkManager.connect(topology);
        networkManager.calcRoutes(topology);
        HashMap<String, ArrayList<Route>> hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap, routers);

        for (Router router : routers.values()) {
            RouteRestController routeController = new RouteRestController(router.getMngIp(), router.getMngPort());
            List<Route> routes = routeController.getRoutes();
            for (Route r : hashMap.get(router.getName())) {
                assertTrue(containsRoute(routes, r));
            }
        }
        CleanUtils cleanUtils = new CleanUtils();
        cleanUtils.cleanUp(topology.getNetworkImpls(), routers);
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

    private ArrayList<TopologyNetwork> prepareComplicatedTopology(HashMap<String, Router> routers) {
        ArrayList<TopologyNetwork> topology = new ArrayList<>();

        TopologyNetwork n1 = new TopologyNetwork();
        n1.setName("TestNetwork1");
        n1.setIpAddress("192.168.1.0");
        n1.setMask(28);

        TopologyNetwork n2 = new TopologyNetwork();
        n2.setName("TestNetwork2");
        n2.setIpAddress("192.168.1.16");
        n2.setMask(28);

        TopologyNetwork n3 = new TopologyNetwork();
        n3.setName("TestNetwork3");
        n3.setIpAddress("192.168.1.32");
        n3.setMask(28);
        topology.addAll(Arrays.asList(n1, n2, n3));

        prepareOnStartTopology(topology, routers);
        return topology;
    }

    private void prepareOnStartTopology(ArrayList<TopologyNetwork> topology, HashMap<String, Router> routers) {
        Integer routNeedCalc = topology.size() + 1;
        TopologyNetwork n1 = topology.get(0);
        TopologyNetwork n2 = topology.get(1);
        TopologyNetwork n3 = topology.get(2);

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
*/

}
