package com.redhat.patriot.network_controll.model.routes;

import com.redhat.patriot.network_controll.model.Router;

/**
 * Wrapper class for Router and network. Representing next hop in the network topology.
 */
public class NextHop {
    private Router router;
    private Integer network;

    /**
     * Instantiates a new Next hop.
     *
     * @param router  the router
     * @param network the network
     */
    public NextHop(Router router, Integer network) {
        this.router = router;
        this.network = network;
    }

    /**
     * Gets router.
     *
     * @return the router
     */
    public Router getRouter() {
        return router;
    }

    /**
     * Sets router.
     *
     * @param router the router
     */
    public void setRouter(Router router) {
        this.router = router;
    }

    /**
     * Gets network.
     *
     * @return the network
     */
    public Integer getNetwork() {
        return network;
    }

    /**
     * Sets network.
     *
     * @param network the network
     */
    public void setNetwork(Integer network) {
        this.network = network;
    }

}
