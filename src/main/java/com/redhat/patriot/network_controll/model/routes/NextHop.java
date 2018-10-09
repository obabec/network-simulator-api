package com.redhat.patriot.network_controll.model.routes;

import com.redhat.patriot.network_controll.model.Router;

/**
 * The type Next hop.
 */
public class NextHop {
    /**
     * The Router.
     */
    Router router;

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
     * The Network.
     */
    Integer network;


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
}
