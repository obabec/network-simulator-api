package com.redhat.patriot.network_controll;

import com.redhat.patriot.network_controll.api.iptables.chain.Chain;
import com.redhat.patriot.network_controll.api.iptables.rules.FilterRule;


/**
 * The type Main.
 */
public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

/*        IPTablesController controller = new IPTablesController("172.17.0.3", 8080);
        Chain chain = new Chain("testerino", "filter");
        FilterRule filterRule = prepareRule(chain);

        int responseChain = controller.addChain(chain);
        int responseRule = controller.addFilterRule(filterRule);
        int saveRest = controller.saveIpTables();

        controller.deleleteFilterRule(filterRule);
        controller.deleteChain(chain);
        controller.saveIpTables();

        RouteController routeController = new RouteController("localhost", 5000);
        Route route = new Route("172.17.0.0", 1400, 16,"16", "172.17.0.2");
        List<NetworkInterface> ifa = routeController.getNetworkInterfaces();
        Route defaultR = new Route("172.17.0.2");
        routeController.addDefaultGW(defaultR);
        routeController.delDefaultGW(defaultR);
        List<Route> routes = routeController.getRouters();
        routeController.addRoute(route);*/

        FloydWarshallDemo floydWarshallDemo = new FloydWarshallDemo();
        floydWarshallDemo.deploy();


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
