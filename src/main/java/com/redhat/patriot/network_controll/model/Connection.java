package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.iproute.Route;

public class Connection {
    Network source;
    Router router;
    Network destination;

    public Network getSource() {
        return source;
    }

    public void setSource(Network source) {
        this.source = source;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Network getDestination() {
        return destination;
    }

    public void setDestination(Network destination) {
        this.destination = destination;
    }

    public Connection(Network source, Router router, Network destination) {
        this.source = source;
        this.router = router;
        this.destination = destination;
    }
}
