/*
 * Copyright 2018 Patriot project
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

package com.redhat.patriot.network.simulator.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.redhat.patriot.network.simulator.api.CalculatedRouteList;
import com.redhat.patriot.network.simulator.api.model.routes.CalcRoute;

/**
 * Network class representing docker network with additional informations
 * like calculated routes to other networks.
 */
public class Network {
    @JsonIgnore
    private CalculatedRouteList<CalcRoute> calcRoutes = new CalculatedRouteList();
    private String ipAddress;
    private Integer mask;
    @JsonIgnore
    private String name;
    @JsonIgnore
    private Boolean internet = false;

    /**
     * Instantiates a new Network.
     */


    public Network() {
    }

    public Network(String ipAddress, Integer mask) {
        this.ipAddress = ipAddress;
        this.mask = mask;
    }

    /**
     * Instantiates a new Network.
     *
     * @param calcRoutes the calc routes
     * @param name       the name
     */
    public Network(CalculatedRouteList<CalcRoute> calcRoutes, String name) {
        this.calcRoutes = calcRoutes;
        this.name = name;
    }

    /**
     * Instantiates a new Network.
     *
     * @param calcRoutes the calc routes
     */
    public Network(CalculatedRouteList<CalcRoute> calcRoutes) {
        this.calcRoutes = calcRoutes;
    }

    /**
     * Gets calc routes.
     *
     * @return the calc routes
     */
    public CalculatedRouteList<CalcRoute> getCalcRoutes() {
        return calcRoutes;
    }

    /**
     * Sets calc routes.
     *
     * @param calcRoutes the calc routes
     */
    public void setCalcRoutes(CalculatedRouteList<CalcRoute> calcRoutes) {
        this.calcRoutes = calcRoutes;
    }

    /**
     * Gets ip address.
     *
     * @return the ip address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Sets ip address.
     *
     * @param ipAddress the ip address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Gets mask.
     *
     * @return the mask
     */
    public Integer getMask() {
        return mask;
    }

    /**
     * Sets mask.
     *
     * @param mask the mask
     */
    public void setMask(Integer mask) {
        this.mask = mask;
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
     * Gets ip with mask.
     *
     * @return the ip with mask
     */
    public String getIpWithMask() {
        return ipAddress + "/" + mask;
    }

    /**
     * Gets internet.
     *
     * @return the internet
     */
    public Boolean getInternet() {
        return internet;
    }

    /**
     * Sets internet.
     *
     * @param internet the internet
     */
    public void setInternet(Boolean internet) {
        this.internet = internet;
    }
}
