package com.redhat.patriot.network_controll;

import com.redhat.patriot.network_controll.iproute.Interface;
import com.redhat.patriot.network_controll.iproute.Route;
import com.redhat.patriot.network_controll.iproute.RouteController;
import com.redhat.patriot.network_controll.iptables.IPTablesController;
import com.redhat.patriot.network_controll.iptables.chain.Chain;
import com.redhat.patriot.network_controll.iptables.rules.FilterRule;

import java.util.List;


public class Main {
    public static void main(String[] args) {

/*        IPTablesController controller = new IPTablesController("172.17.0.3", 8080);
        Chain chain = new Chain("testerino", "filter");
        FilterRule filterRule = prepareRule(chain);

        int responseChain = controller.addChain(chain);
        int responseRule = controller.addFilterRule(filterRule);
        int saveRest = controller.saveIpTables();

        controller.deleleteFilterRule(filterRule);
        controller.deleteChain(chain);
        controller.saveIpTables();*/

        RouteController routeController = new RouteController("localhost", 5000);
        Route route = new Route("172.17.0.0", 1400, 16,"16", "172.17.0.2");
        //List<Interface> ifa = routeController.getInterfaces();
        Route defaultR = new Route("172.17.0.2");
        routeController.addDefaultGW(defaultR);
        routeController.delDefaultGW(defaultR);
        List<Route> routes = routeController.getRouters();
        routeController.addRoute(route);


    }

    private static FilterRule prepareRule(Chain chain) {

        FilterRule filterRule = new FilterRule();
        filterRule.attributes.put("action", "DROP");
        filterRule.attributes.put("chain", chain.getName());
        filterRule.attributes.put("proto", "icmp");
        filterRule.attributes.put("source", "19.18.17.16");
        filterRule.attributes.put("destination", "13.12.11.10");
        return filterRule;
    }
}
