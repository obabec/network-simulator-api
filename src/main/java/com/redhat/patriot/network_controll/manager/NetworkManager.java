package com.redhat.patriot.network_controll.manager;


import com.redhat.patriot.network_controll.model.Connection;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;

import java.util.List;

public interface NetworkManager {
    Connection connectNetworks(Network source, Network dest, List<Router> routers);
    void disconnectNetwork(Connection connection);
    void stopTrafficToNetwork(Network network, Router router);
    void enableTrafficToNetwork(Network network, Router router);

}
