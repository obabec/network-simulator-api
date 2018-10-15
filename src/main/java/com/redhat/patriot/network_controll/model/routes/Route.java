package com.redhat.patriot.network_controll.model.routes;

import com.redhat.patriot.network_controll.api.iproute.NetworkInterface;
import com.redhat.patriot.network_controll.model.Network;
import com.redhat.patriot.network_controll.model.Router;

/**
 * The type Route.
 */
public class Route {
    private Network source;
    private Network dest;
    private NetworkInterface rNetworkInterface;
    private Router targetRouter;
    private Integer mtu = 1400;
    private Integer hopLimit = 16;

    /**
     * Gets dest.
     *
     * @return the dest
     */
    public Network getDest() {
        return dest;
    }

    /**
     * Sets dest.
     *
     * @param dest the dest
     */
    public void setDest(Network dest) {
        this.dest = dest;
    }

    /**
     * Gets source.
     *
     * @return the source
     */
    public Network getSource() {
        return source;
    }

    /**
     * Sets source.
     *
     * @param source the source
     */
    public void setSource(Network source) {
        this.source = source;
    }

    /**
     * Gets network interface.
     *
     * @return the network interface
     */
    public NetworkInterface getrNetworkInterface() {
        return rNetworkInterface;
    }

    /**
     * Sets network interface.
     *
     * @param rNetworkInterface the r network interface
     */
    public void setrNetworkInterface(NetworkInterface rNetworkInterface) {
        this.rNetworkInterface = rNetworkInterface;
    }

    /**
     * Gets target router.
     *
     * @return the target router
     */
    public Router getTargetRouter() {
        return targetRouter;
    }

    /**
     * Sets target router.
     *
     * @param targetRouter the target router
     */
    public void setTargetRouter(Router targetRouter) {
        this.targetRouter = targetRouter;
    }

    /**
     * To path string.
     *
     * @return the string
     */
    public String toPath() {
        return dest.getIpAddress() + "/" + dest.getMask()
                + "/" + rNetworkInterface.getIp() + "/" + mtu + "/" + hopLimit;
    }
}
