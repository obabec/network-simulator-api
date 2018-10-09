package com.redhat.patriot.network_controll.api.iproute;

/**
 * The type NetworkInterface.
 */
public class NetworkInterface {
    /**
     * The Name.
     */
    String name;
    /**
     * The Ip.
     */
    String ip;
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
     * Instantiates a new NetworkInterface.
     *
     * @param name the name
     * @param ip   the ip
     * @param mask the mask
     */
    public NetworkInterface(String name, String ip, Integer mask) {
        this.name = name;
        this.ip = ip;
        this.mask = mask;
    }

    /**
     * Instantiates a new NetworkInterface.
     */
    public NetworkInterface() {
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
     * Gets ip.
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets ip.
     *
     * @param ip the ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}
