package com.redhat.patriot.network_controll.iptables.chain;

public class Chain {
    private String name;
    private String table;

    public Chain(String name, String table) {
        this.name = name;
        this.table = table;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getPath() {
        return "/chain/" + table + "/" + name + "/";
    }
}
