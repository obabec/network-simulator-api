package com.redhat.patriot.network_controll.iproute;

public class Route {

    private String dest;
    private Integer mtu;
    private Integer hopLimit;
    private String mask;
    private String gateway;

    public Route() {
    }

    public String toPath() {
        return dest + "/" + mask + "/" + gateway + "/" + mtu + "/" + hopLimit;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(Integer mtu) {
        this.mtu = mtu;
    }

    public Integer getHopLimit() {
        return hopLimit;
    }

    public void setHopLimit(Integer hopLimit) {
        this.hopLimit = hopLimit;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }


    public Route(String dest, Integer mtu, Integer hopLimit, String mask, String gateway) {
        this.dest = dest;
        this.mtu = mtu;
        this.hopLimit = hopLimit;
        this.mask = mask;
        this.gateway = gateway;
    }

    public Route(String gateway) {
        this.gateway = gateway;
        this.dest = "default";
    }
}
