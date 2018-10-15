package com.redhat.patriot.network_controll.api.iptables.chain;

/**
 * IpTables chain.
 */
public class Chain {
    private String name;
    private String table;

    /**
     * Instantiates a new Chain.
     *
     * @param name  the name
     * @param table the table
     */
    public Chain(String name, String table) {
        this.name = name;
        this.table = table;
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
     * Gets table.
     *
     * @return the table
     */
    public String getTable() {
        return table;
    }

    /**
     * Sets table.
     *
     * @param table the table
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return "/chain/" + table + "/" + name + "/";
    }
}
