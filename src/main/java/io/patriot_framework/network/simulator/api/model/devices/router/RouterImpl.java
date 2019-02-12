/*
 * Copyright 2019 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.network.simulator.api.model.devices.router;

import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;

import java.util.ArrayList;
import java.util.List;

/**
 * RouterImpl class represents docker container built as router with network-control apis.
 */
public class RouterImpl implements Router {
    private String name;
    private List<NetworkInterface> networkInterfaces;
    private List<TopologyNetwork> connectedTopologyNetworks;
    private String mngIp;
    private Integer mngPort;
    private Boolean defaultGW = false;
    private String creator;


    // Default http py-route rest api
    public static final int DEFAULT_PORT = 5000;

    /**
     * Instantiates a new RouterImpl.
     *
     * @param name the name
     */
    public RouterImpl(String name) {
        this.name = name;
        connectedTopologyNetworks = new ArrayList<>();
        mngPort = DEFAULT_PORT;
    }

    /**
     * Instantiates a new RouterImpl.
     *
     * @param name              the name
     * @param networkInterfaces the network interfaces
     */
    public RouterImpl(String name, List<NetworkInterface> networkInterfaces) {
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

    @Override
    public List<NetworkInterface> getInterfaces() {
        return networkInterfaces;
    }


    @Override
    public String getIPAddress() {
        return mngIp;
    }

    @Override
    public void setIPAddress(String ipAddress) {
        this.mngIp = ipAddress;
    }

    @Override
    public List<TopologyNetwork> getConnectedNetworks() {
        return connectedTopologyNetworks;
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
    public List<TopologyNetwork> getConnectedTopologyNetworks() {
        return connectedTopologyNetworks;
    }

    /**
     * Sets connected networks.
     *
     * @param connectedTopologyNetworks the connected networks
     */
    public void setConnectedTopologyNetworks(List<TopologyNetwork> connectedTopologyNetworks) {
        this.connectedTopologyNetworks = connectedTopologyNetworks;
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

    @Override
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
