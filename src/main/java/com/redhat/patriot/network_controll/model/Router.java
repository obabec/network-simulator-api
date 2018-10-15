package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.api.iproute.NetworkInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Router class.
 */
public class Router {
    private String name;
    private List<NetworkInterface> networkInterfaces;
    private List<Network> connectedNetworks;
    private String mngIp;
    private Integer mngPort;
    private Boolean defaultGW = false;

    /**
     * Instantiates a new Router.
     *
     * @param name the name
     */
    public Router(String name) {
        this.name = name;
        connectedNetworks = new ArrayList<>();
        mngPort = 5000;
    }

    /**
     * Instantiates a new Router.
     *
     * @param name              the name
     * @param networkInterfaces the network interfaces
     */
    public Router(String name, List<NetworkInterface> networkInterfaces) {
        this.name = name;
        this.networkInterfaces = networkInterfaces;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Gets network interfaces.
     *
     * @return the network interfaces
     */
    public List<NetworkInterface> getNetworkInterfaces() {
        return networkInterfaces;
    }

    /**
     * Sets network interfaces.
     *
     * @param networkInterfaces the network interfaces
     */
    public void setNetworkInterfaces(List<NetworkInterface> networkInterfaces) {
        this.networkInterfaces = networkInterfaces;
    }

    /**
     * Gets connected networks.
     *
     * @return the connected networks
     */
    public List<Network> getConnectedNetworks() {
        return connectedNetworks;
    }

    /**
     * Sets connected networks.
     *
     * @param connectedNetworks the connected networks
     */
    public void setConnectedNetworks(List<Network> connectedNetworks) {
        this.connectedNetworks = connectedNetworks;
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
     * Gets mng port.
     *
     * @return the mng port
     */
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

    public Boolean getDefaultGW() {
        return defaultGW;
    }

    public void setDefaultGW(Boolean defaultGW) {
        this.defaultGW = defaultGW;
    }
}
