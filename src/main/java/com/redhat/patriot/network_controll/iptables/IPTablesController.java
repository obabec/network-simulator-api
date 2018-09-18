package com.redhat.patriot.network_controll.iptables;

import com.redhat.patriot.network_controll.Controller;
import com.redhat.patriot.network_controll.iptables.chain.Chain;
import com.redhat.patriot.network_controll.iptables.rules.FilterRule;

public class IPTablesController extends Controller {

    public IPTablesController(String ip, Integer port) {
        super(ip, port);
    }

    public String addFilterRule(FilterRule filterRule) {

        return executeHttpRequest(filterRule.toPath(), "PUT");
    }

    public String deleleteFilterRule(FilterRule filterRule) {
        return executeHttpRequest(filterRule.toPath(), "DELETE");
    }


    public String addChain(Chain chain) {
        return executeHttpRequest(chain.getPath(), "PUT");
    }

    public String deleteChain(Chain chain) {
        return executeHttpRequest(chain.getPath(), "DELETE");
    }


    public String saveIpTables() {
        return executeHttpRequest("/save/", "GET");
    }
}
