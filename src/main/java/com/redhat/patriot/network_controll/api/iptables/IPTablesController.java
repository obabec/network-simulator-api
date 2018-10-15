package com.redhat.patriot.network_controll.api.iptables;

import com.redhat.patriot.network_controll.api.Controller;
import com.redhat.patriot.network_controll.api.iptables.chain.Chain;
import com.redhat.patriot.network_controll.api.iptables.rules.FilterRule;

/**
 * The type Ip tables controller.
 */
public class IPTablesController extends Controller {

    /**
     * Instantiates a new Ip tables controller.
     *
     * @param ip   the ip
     * @param port the port
     */
    public IPTablesController(String ip, Integer port) {
        super(ip, port);
    }

    /**
     * Add filter rule string.
     *
     * @param filterRule the filter rule
     * @return the string
     */
    public String addFilterRule(FilterRule filterRule) {

        return executeHttpRequest(filterRule.toPath(), "PUT");
    }

    /**
     * Delete filter rule string.
     *
     * @param filterRule the filter rule
     * @return the string
     */
    public String deleteFilterRule(FilterRule filterRule) {
        return executeHttpRequest(filterRule.toPath(), "DELETE");
    }


    /**
     * Add chain string.
     *
     * @param chain the chain
     * @return the string
     */
    public String addChain(Chain chain) {
        return executeHttpRequest(chain.getPath(), "PUT");
    }

    /**
     * Delete chain string.
     *
     * @param chain the chain
     * @return the string
     */
    public String deleteChain(Chain chain) {
        return executeHttpRequest(chain.getPath(), "DELETE");
    }


    /**
     * Save ip tables string.
     *
     * @return the string
     */
    public String saveIpTables() {
        return executeHttpRequest("/save/", "GET");
    }
}
