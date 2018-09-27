package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.iproute.Interface;
import com.redhat.patriot.network_controll.iproute.Route;

import java.util.ArrayList;
import java.util.List;

public class Network {
    private String ip;

    //TODO: Remake String IP address to int/long. Add attribute ip range to Network, for selection right interface on router

    private Interface mask;



    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Interface getMask() {
        return mask;
    }

    public void setMask(Interface mask) {
        this.mask = mask;
    }

    List<Router> connectedRouters = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public void initializeConnections() throws Exception {
        if (connectedRouters.isEmpty()) {
            throw new Exception("Router are not provided yet");
        } else {
            for (Router router : connectedRouters) {
                for (Route route : router.getRoutes()) {
                    connections.add(new Connection(this, router, ))
                }

            }
        }
    }
}
