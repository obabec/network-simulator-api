package com.redhat.patriot.network_controll.api.iptables.rules;

import java.util.LinkedHashMap;


/**
 * The type Filter rule.
 */
public class FilterRule implements Rule {
    private LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

    /**
     * Instantiates a new Filter rule with default attributes.
     */
    public FilterRule() {

        attributes.put("action", null);
        attributes.put("chain", null);
        attributes.put("proto", null);
        attributes.put("iface-in", "any");
        attributes.put("iface-out", "any");
        attributes.put("source", null);
        attributes.put("destination", null);
    }

    @Override
    public String toPath() {
        String rules= "/rules/";
        for (String value : attributes.values()) {
            rules += value + "/";
        }
        return rules;
    }

    /**
     * Gets attributes.
     *
     * @return the attributes
     */
    public LinkedHashMap<String, String> getAttributes() {
        return attributes;
    }
}
