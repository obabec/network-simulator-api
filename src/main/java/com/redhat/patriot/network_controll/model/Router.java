package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.iproute.Interface;
import com.redhat.patriot.network_controll.iproute.Route;

import java.util.List;

public class Router {
   private List<Interface> interfaces;
   private List<Route> routes;
   private String mngIP;
}
