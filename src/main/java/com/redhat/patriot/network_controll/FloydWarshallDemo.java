package com.redhat.patriot.network_controll;

import com.redhat.patriot.network_controll.api.iproute.NetworkInterface;
import com.redhat.patriot.network_controll.manager.NetworkManager;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;
import com.redhat.patriot.network_controll.model.routes.CalcRoute;
import com.redhat.patriot.network_controll.model.routes.NextHop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * The type Floyd marshall test.
 */
public class FloydWarshallDemo {

    /**
     * Deploy.
     */
    public void deploy() {
        ArrayList<Network> topology = new ArrayList<>(4);
        List<NetworkInterface> ifs = new ArrayList<>();
        List<Router> routers = new ArrayList<>();



        ifs.add(new NetworkInterface("en0", "192.168.0.1", 28));
        ifs.add(new NetworkInterface("en1", "192.168.16.1", 28));

        routers.add(new Router("R1", ifs));

        ifs = new ArrayList<>();
        ifs.add(new NetworkInterface("en0", "192.168.16.2", 28));
        ifs.add(new NetworkInterface("en1", "192.168.32.1", 28));

        routers.add(new Router("R2", ifs));

        ifs = new ArrayList<>();
        ifs.add(new NetworkInterface("en0", "192.168.16.3", 28));
        ifs.add(new NetworkInterface("en1", "192.168.48.1", 28));

        routers.add(new Router("R3", ifs));

        ifs = new ArrayList<>();
        ifs.add(new NetworkInterface("en0", "192.168.32.2", 28));
        ifs.add(new NetworkInterface("en1", "192.168.48.2", 28));

        routers.add(new Router("R5", ifs));

        Network n1 = new Network();
        Network n2 = new Network();
        Network n3 = new Network();
        Network n4 = new Network();

        topology.addAll(Arrays.asList(n1, n2, n3, n4));

        Integer routNeedCalc = topology.size() + 1;

        n1.getCalcRoute().add(new CalcRoute(new NextHop(null, 0), null));
        n1.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(0), 1), 1));
        n1.getCalcRoute().add(new CalcRoute(new NextHop(null, 2), routNeedCalc));
        n1.getCalcRoute().add(new CalcRoute(new NextHop(null, 3), routNeedCalc));
        n1.setMask(28);
        n1.setIpAddress("192.168.0.0");


        n2.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(0), 0), 1));
        n2.getCalcRoute().add(new CalcRoute(new NextHop(null, 1), null));
        n2.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(1), 2), 1));
        n2.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(2), 3), 1));
        n2.setMask(28);
        n2.setIpAddress("192.168.16.0");



        n3.getCalcRoute().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n3.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(1), 1), 1));
        n3.getCalcRoute().add(new CalcRoute(new NextHop(null, 2), null));
        n3.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(3), 3), 1));
        n3.setMask(28);
        n3.setIpAddress("192.168.32.0");



        n4.getCalcRoute().add(new CalcRoute(new NextHop(null, 0), routNeedCalc));
        n4.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(2), 1), 1));
        n4.getCalcRoute().add(new CalcRoute(new NextHop(routers.get(3), 2), 1));
        n4.getCalcRoute().add(new CalcRoute(new NextHop(null, 3), null));
        n4.setMask(28);
        n4.setIpAddress("192.168.48.0");

        NetworkManager networkManager = new NetworkManager();


        networkManager.calcRoutes(topology);
        HashMap hashMap = networkManager.processRoutes(topology);
        networkManager.setRoutes(hashMap);
    }
}
