package com.redhat.patriot.network_controll.iptables.rules;

public interface Rule {
    boolean validate();
    String toPath();
}
