package com.redhat.patriot.network_controll.api.iproute;

/**
 * Router' s network interface
 */
public class NetworkInterface {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Ip.
     */
    private String ip;
    /**
     * The Mask.
     */
    private Integer mask;

    /**
     * Instantiates a new Network interface.
     *
     * @param name the network name
     * @param ip   the ip address
     * @param mask the mask
     */
    public NetworkInterface(String name, String ip, Integer mask) {
        this.name = name;
        this.ip = ip;
        this.mask = mask;
    }

    /**
     * Instantiates a new Network interface.
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

}
