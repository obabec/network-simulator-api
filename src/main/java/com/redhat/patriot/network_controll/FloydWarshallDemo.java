package com.redhat.patriot.network_controll;

import com.redhat.patriot.network_controll.manager.NetworkManager;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;
import com.redhat.patriot.network_controll.model.routes.CalcRoute;
import com.redhat.patriot.network_controll.model.routes.NextHop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FloydWarshallDemo {

    public void deploy() {
        ArrayList<Network> topology = new ArrayList<>(4);
        HashMap<String, Router> routers = new HashMap<>();

        routers.put("R1", new Router("R1"));

        routers.put("R2", new Router("R2"));

        routers.put("R3", new Router("R3"));

        routers.put("R5", new Router("R5"));

        Network n1 = new Network();
        n1.setIpAddress("192.168.0.0");
        n1.setMask(28);

        Network n2 = new Network();
        n2.setMask(28);
        n2.setIpAddress("192.168.16.0");
        n2.setName("N2");

        Network n3 = new Network();
        n3.setName("N3");
        n3.setMask(28);
        n3.setIpAddress("192.168.32.0");

        Network n4 = new Network();
        n4.setMask(28);
        n4.setIpAddress("192.168.48.0");
        n4.setName("N4");

        Network internet = new Network();
        internet.setInternet(true);

        topology.addAll(Arrays.asList(n1, n2, n3, n4, internet));
        initNetworks(topology, routers);
        NetworkManager networkManager = new NetworkManager();

        routers = networkManager.connect(topology, routers);
        networkManager.calcRoutes(topology);
        HashMap hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap, routers);
    }

    private void initNetworks(ArrayList<Network> topology, HashMap<String, Router> routers) {

        Integer routNeedCalc = topology.size() + 1;
        Network n1 = topology.get(0);
        Network n2 = topology.get(1);
        Network n3 = topology.get(2);
        Network n4 = topology.get(3);
        Network internet = topology.get(4);

        n1.setName("N1");
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), null));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 1), 1));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), routNeedCalc));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 3), routNeedCalc));
        n1.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), routNeedCalc));

        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R1"), 0), 1));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), null));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 2), 1));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R3"), 3), 1));
        n2.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), routNeedCalc));

        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R2"), 1), 1));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(null, 2), null));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 3), 1));
        n3.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 4), 1));

        n4.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R3"), 1), 1));
        n4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 2), 1));
        n4.getCalcRoutes().add(new CalcRoute(new NextHop(null, 3), null));
        n4.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 4), 1));

        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 1), routNeedCalc));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 2), 1));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(routers.get("R5"), 3), 1));
        internet.getCalcRoutes().add(new CalcRoute(new NextHop(null, 4), null));

    }
}
