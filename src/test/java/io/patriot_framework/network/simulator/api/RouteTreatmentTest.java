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
import io.patriot_framework.network.simulator.api.manager.Manager;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.router.NetworkInterface;
import io.patriot_framework.network.simulator.api.model.devices.router.Router;
import io.patriot_framework.network.simulator.api.model.devices.router.RouterImpl;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import io.patriot_framework.network.simulator.api.model.routes.Route;
import io.patriot_framework.network_simulator.docker.control.DockerController;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RouteTreatmentTest {

    private Manager manager = new Manager(Arrays.asList(new DockerController()),"patriot");

    @Test
    public void testRouteTreatmentTest() {
        ArrayList<TopologyNetwork> topologyNetworks = new ArrayList<>();
        Topology topology = new Topology(topologyNetworks);
        Router r = new RouterImpl("TestRouter", "Docker");
        Route route = createSimpleTopology(topologyNetworks, false, r);
        manager.processRoutes(topology);
        assertTrue(manager.getProcessedRoutes().get(r.getName()).get(0).toAPIFormat().equals(route.toAPIFormat()));

    }

    private Route parseRoute(TopologyNetwork source, TopologyNetwork dest, Router targetRouter, NetworkInterface targetInterface) {
        Route route = new Route();
        route.setrNetworkInterface(targetInterface);
        route.setSource(source);
        route.setDest(dest);
        route.setTargetRouter(targetRouter);
        return route;
    }

    private Route createSimpleTopology(ArrayList<TopologyNetwork> topology, Boolean duplicate, Router r) {
        List<NetworkInterface> routerInterfaces = new ArrayList<>();
        routerInterfaces.add(new NetworkInterface("eth0", "192.168.0.2", 24));
        r.setNetworkInterfaces(routerInterfaces);

        TopologyNetwork n1 = new TopologyNetwork();
        n1.setName("TestNetwork1");
        n1.setIPAddress("172.16.0.0");
        n1.setCreator("Docker");
        n1.setMask(16);

        TopologyNetwork n2 = new TopologyNetwork();
        n2.setName("TestNetwork2");
        n2.setIPAddress("192.168.0.0");
        n2.setCreator("Docker");
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

    @Test
    public void setRoutesTest() {

        ArrayList<Router> routers = new ArrayList<>();
        routers.add(new RouterImpl("R1", "Docker"));
        routers.add(new RouterImpl("R2", "Docker"));

        ArrayList<TopologyNetwork> topologyNetworks = prepareComplicatedTopology(routers);
        Topology topology = new Topology(routers, topologyNetworks);
        manager.deployTopology(topology);


        for (Router router : routers) {
            RouteRestController routeController = new RouteRestController();
            List<Route> routes = routeController.getRoutes(router.getIPAddress(), router.getMngPort());
            for (Route r : manager.getProcessedRoutes().get(router.getName())) {
                assertTrue(containsRoute(routes, r));
            }
        }
        CleanUtils cleanUtils = new CleanUtils();
        cleanUtils.cleanUp(topology.getNetworks(), routers);
    }

    private Boolean containsRoute(List<Route> routes, Route route) {
        for (Route r : routes) {
            if (r.getDest().getIPAddress().equals(route.getDest().getIPAddress()) &&
                    r.getrNetworkInterface().getIp().equals(route.getrNetworkInterface().getIp())) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<TopologyNetwork> prepareComplicatedTopology(ArrayList<Router> routers) {
        ArrayList<TopologyNetwork> topology = new ArrayList<>();

        TopologyNetwork n1 = new TopologyNetwork();
        n1.setName("TestNetwork1");
        n1.setIPAddress("192.168.1.0");
        n1.setCreator("Docker");
        n1.setMask(28);

        TopologyNetwork n2 = new TopologyNetwork();
        n2.setName("TestNetwork2");
        n2.setIPAddress("192.168.1.16");
        n2.setCreator("Docker");
        n2.setMask(28);

        TopologyNetwork n3 = new TopologyNetwork();
        n3.setName("TestNetwork3");
        n3.setIPAddress("192.168.1.32");
        n3.setCreator("Docker");
        n3.setMask(28);
        topology.addAll(Arrays.asList(n1, n2, n3));

        prepareOnStartTopology(topology, routers);
        return topology;
    }

    private void prepareOnStartTopology(ArrayList<TopologyNetwork> topology, ArrayList<Router> routers) {
        Integer routNeedCalc = topology.size() + 1;
        TopologyNetwork n1 = topology.get(0);
        TopologyNetwork n2 = topology.get(1);
        TopologyNetwork n3 = topology.get(2);

        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), null));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get(0), 1), 1));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), routNeedCalc));


        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get(0), 0), 1));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), null));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get(1), 2), 1));


        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get(1), 1), 1));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), null));

    }

}
