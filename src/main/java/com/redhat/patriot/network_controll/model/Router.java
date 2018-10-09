package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.api.iproute.NetworkInterface;

import java.util.List;

/**
 * The type Router.
 */
public class Router {
    String name;
    List<NetworkInterface> networkInterfaces;
    String mngIp;
    Integer mngPort;


    public Integer getMngPort() {
        return mngPort;
    }

    /**
     * Sets mng port.
     *
     * @param mngPort the mng port
     */
    public void setMngPort(Integer mngPort) {
        this.mngPort = mngPort;
    }

    /**
     * Gets mng ip.
     *
     * @return the mng ip
     */
    public String getMngIp() {
        return mngIp;
    }

    /**
     * Sets mng ip.
     *
     * @param mngIp the mng ip
     */
    public void setMngIp(String mngIp) {
        this.mngIp = mngIp;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets networkInterfaces.
     *
     * @return the networkInterfaces
     */
    public List<NetworkInterface> getNetworkInterfaces() {
        return networkInterfaces;
    }

    /**
     * Sets networkInterfaces.
     *
     * @param networkInterfaces the networkInterfaces
     */
    public void setNetworkInterfaces(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    /**
     * Instantiates a new Router.
     *
     * @param name the name
     */
    public Router(String name) {
        this.name = name;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    public Router(String name, List<NetworkInterface> networkInterfaces) {
        this.name = name;
        this.networkInterfaces = networkInterfaces;
    }
}
