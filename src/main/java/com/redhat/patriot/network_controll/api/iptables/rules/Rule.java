package com.redhat.patriot.network_controll.api.iptables.rules;

/**
 * The interface Rule.
 */
public interface Rule {
    /**
     * Validate boolean.
     *
     * @return the boolean
     */
    boolean validate();

    /**
     * To path string.
     *
     * @return the string
     */
    String toPath();
}
