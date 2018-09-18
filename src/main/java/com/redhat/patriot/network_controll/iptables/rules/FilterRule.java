package com.redhat.patriot.network_controll.iptables.rules;

import java.util.LinkedHashMap;


public class FilterRule implements Rule {
    public LinkedHashMap<String, String> attributes = new LinkedHashMap<>();

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
    public boolean validate() {

        if(attributes.values().contains(null))return false;
        else return true;
    }

    @Override
    public String toPath() {
        if(validate()) {
            String rules= "/rules/";
            for (String value : attributes.values()) {
                rules += value + "/";
            }
            return rules;
        } else {
            return null;
        }
    }
}
