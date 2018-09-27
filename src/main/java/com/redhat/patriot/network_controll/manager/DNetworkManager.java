package com.redhat.patriot.network_controll.manager;

import com.redhat.patriot.network_controll.iproute.Route;
import com.redhat.patriot.network_controll.model.Connection;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;

import java.util.List;

public class DNetworkManager implements NetworkManager {

    @Override
    public Connection connectNetworks(Network source, Network dest, List<Router> routers) {
        Route route = new Route(dest.getIp(), 1400, 16, dest.getMask(), )
    }

    @Override
    public void disconnectNetwork(Connection connection) {

    }

    @Override
    public void stopTrafficToNetwork(Network network, Router router) {

    }

    @Override
    public void enableTrafficToNetwork(Network network, Router router) {

    }
}
