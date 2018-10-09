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

    public Network getDest() {
        return dest;
    }

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
     * Gets interface.
     *
     * @return the interface
     */
    public NetworkInterface getrNetworkInterface() {
        return rNetworkInterface;
    }

    /**
     * Sets interface.
     *
     * @param rNetworkInterface the r interface
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

    public String toPath() {
        return dest.getIpAddress() + "/" + dest.getMask() + "/" + rNetworkInterface.getIp() + "/" + mtu + "/" + hopLimit;
    }
}
