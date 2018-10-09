package com.redhat.patriot.network_controll.model;

import com.redhat.patriot.network_controll.CalculatedRouteList;
import com.redhat.patriot.network_controll.model.routes.CalcRoute;

/**
 * The type Network.
 */
public class Network {
    /**
     * The Calc route.
     */
    CalculatedRouteList<CalcRoute> calcRoute = new CalculatedRouteList();
    /**
     * The Ip address.
     */
    String ipAddress;
    /**
     * The Mask.
     */
    Integer mask;

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
     * Instantiates a new Network.
     *
     * @param calcRoute the calc route
     */
    public Network(CalculatedRouteList<CalcRoute> calcRoute) {
        this.calcRoute = calcRoute;
    }

    /**
     * Instantiates a new Network.
     */
    public Network() {
    }

    /**
     * Gets calc route.
     *
     * @return the calc route
     */
    public CalculatedRouteList<CalcRoute> getCalcRoute() {
        return calcRoute;
    }

    /**
     * Sets calc route.
     *
     * @param calcRoute the calc route
     */
    public void setCalcRoute(CalculatedRouteList<CalcRoute> calcRoute) {
        this.calcRoute = calcRoute;
    }
}
