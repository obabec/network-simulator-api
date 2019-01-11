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

import io.patriot_framework.network.simulator.api.manager.NetworkManager;
import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FloydWarshallTest {

    private ArrayList<Network> copyTopology(ArrayList<Network> topology) {
         ArrayList<Network> resultTop = new ArrayList<>();
         for (int i = 0; i < topology.size(); i++) {
             Network n = new Network();
             n.setInternet(topology.get(i).getInternet());
             n.setName(topology.get(i).getName());
             n.setMask(topology.get(i).getMask());
             n.setIpAddress(topology.get(i).getIpAddress());
             resultTop.add(n);
         }
         return resultTop;
    }

    private ArrayList<Network> prepareResultTopology(ArrayList<Network> topology) {
        ArrayList<Network> resultTop = copyTopology(topology);
        CalculatedRouteList n1Routes = new CalculatedRouteList();
        n1Routes.add(new CalcRoute(new NextHop(null, 0), null));
        n1Routes.add(new CalcRoute(new NextHop(null, 1), 1));
        n1Routes.add(new CalcRoute(new NextHop(null, 1), 2));
        n1Routes.add(new CalcRoute(new NextHop(null, 1), 2));
        n1Routes.add(new CalcRoute(new NextHop(null, 1), 3));
        resultTop.get(0).setCalcRoutes(n1Routes);

        CalculatedRouteList n2Routes = new CalculatedRouteList();
        n2Routes.add(new CalcRoute(new NextHop(null, 0), 1));
        n2Routes.add(new CalcRoute(new NextHop(null, 1), null));
        n2Routes.add(new CalcRoute(new NextHop(null, 2), 1));
        n2Routes.add(new CalcRoute(new NextHop(null, 3), 1));
        n2Routes.add(new CalcRoute(new NextHop(null, 2), 2));
        resultTop.get(1).setCalcRoutes(n2Routes);

        CalculatedRouteList n3Routes = new CalculatedRouteList();
        n3Routes.add(new CalcRoute(new NextHop(null, 1), 2));
        n3Routes.add(new CalcRoute(new NextHop(null, 1), 1));
        n3Routes.add(new CalcRoute(new NextHop(null, 2), null));
        n3Routes.add(new CalcRoute(new NextHop(null, 3), 1));
        n3Routes.add(new CalcRoute(new NextHop(null, 4), 1));
        resultTop.get(2).setCalcRoutes(n3Routes);

        CalculatedRouteList n4Routes = new CalculatedRouteList();
        n4Routes.add(new CalcRoute(new NextHop(null, 1), 2));
        n4Routes.add(new CalcRoute(new NextHop(null, 1), 1));
        n4Routes.add(new CalcRoute(new NextHop(null, 2), 1));
        n4Routes.add(new CalcRoute(new NextHop(null, 3), null));
        n4Routes.add(new CalcRoute(new NextHop(null, 4), 1));
        resultTop.get(3).setCalcRoutes(n4Routes);

        CalculatedRouteList n5Routes = new CalculatedRouteList();
        n5Routes.add(new CalcRoute(new NextHop(null, 2), 3));
        n5Routes.add(new CalcRoute(new NextHop(null, 2), 2));
        n5Routes.add(new CalcRoute(new NextHop(null, 2), 1));
        n5Routes.add(new CalcRoute(new NextHop(null, 3), 1));
        n5Routes.add(new CalcRoute(new NextHop(null, 4), null));
        resultTop.get(4).setCalcRoutes(n5Routes);

        return resultTop;
    }

    /*@Test
    public void FloydWarshallTest() {

        ArrayList<Network> topology = new ArrayList<>(4);
        HashMap<String, Router> routers = new HashMap<>();

        routers.put("R1", new Router("R1"));
        routers.put("R2", new Router("R2"));
        routers.put("R3", new Router("R3"));
        routers.put("R5", new Router("R5"));

        Network n1 = new Network();
        n1.setIpAddress("192.168.0.0");
        n1.setName("TN1");
        n1.setMask(28);

        Network n2 = new Network();
        n2.setMask(28);
        n2.setIpAddress("192.168.16.0");
        n2.setName("TN2");

        Network n3 = new Network();
        n3.setName("TN3");
        n3.setMask(28);
        n3.setIpAddress("192.168.32.0");

        Network n4 = new Network();
        n4.setMask(28);
        n4.setIpAddress("192.168.48.0");
        n4.setName("TN4");

        Network internet = new Network();
        internet.setInternet(true);

        topology.addAll(Arrays.asList(n1, n2, n3, n4, internet));
        initNetworks(topology, routers);
        NetworkManager networkManager = new NetworkManager();
        routers = networkManager.connect(topology, routers);
        ArrayList<Network> resArr = prepareResultTopology(topology);
        networkManager.calcRoutes(topology);

        try {
            for (int i = 0; i < 5; i++) {
                Network targetNetwork = topology.get(i);
                Network resultNetwork = resArr.get(i);
                for (int y = 0; y < 5; y++) {
                    Assertions.assertEquals(targetNetwork.getCalcRoutes().get(y).getCost(),
                            resultNetwork.getCalcRoutes().get(y).getCost());

                    Assertions.assertEquals(targetNetwork.getCalcRoutes().get(y).getNextHop().getNetwork(),
                            resultNetwork.getCalcRoutes().get(y).getNextHop().getNetwork());
                }
            }
        } finally {
            CleanUtils cleanUtils = new CleanUtils();
            topology.remove(4);
            cleanUtils.cleanUp(topology, routers);
        }
    }*/


    private void initNetworks(ArrayList<Network> topology, HashMap<String, Router> routers) {

        Integer routNeedCalc = topology.size() + 1;
        Network tN1 = topology.get(0);
        Network tN2 = topology.get(1);
        Network tN3 = topology.get(2);
        Network tN4 = topology.get(3);
        Network internet = topology.get(4);

        tN1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), null));
        tN1.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 1), 1));
        tN1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), routNeedCalc));
        tN1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 3), routNeedCalc));
        tN1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), routNeedCalc));

        tN2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 0), 1));
        tN2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), null));
        tN2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 2), 1));
        tN2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R3"), 3), 1));
        tN2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), routNeedCalc));

        tN3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        tN3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 1), 1));
        tN3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), null));
        tN3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 3), 1));
        tN3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 4), 1));

        tN4.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        tN4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R3"), 1), 1));
        tN4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 2), 1));
        tN4.getCalcRoutes().add(new CalcRoute(new NextHop(null, 3), null));
        tN4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 4), 1));

        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), routNeedCalc));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 2), 1));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 3), 1));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), null));
    }
}
