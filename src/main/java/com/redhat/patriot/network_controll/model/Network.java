package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.iproute.Interface;

import java.util.List;

public class Network {
    private String ip;
    private Interface mask;
    List<Router> connectedRouters;
    private List<Connection> connections;
}
