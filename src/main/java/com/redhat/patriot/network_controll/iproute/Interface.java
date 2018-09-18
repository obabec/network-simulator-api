package com.redhat.patriot.network_controll.iproute;

public class Interface {
    String name;
    String ip;

    public Interface(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public Interface() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
