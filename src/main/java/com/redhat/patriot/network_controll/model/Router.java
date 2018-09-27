package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.iproute.Interface;
import com.redhat.patriot.network_controll.iproute.Route;

import java.util.List;

public class Router {
   private List<Interface> interfaces;
   private List<Route> routes;
   private List<Network> connectedNetworks;
   private String mngIP;

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces = interfaces;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public String getMngIP() {
        return mngIP;
    }

    public void setMngIP(String mngIP) {
        this.mngIP = mngIP;
    }
}
