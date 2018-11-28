/*
 * Copyright 2018 Patriot project
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package io.patriot_framework.network.simulator.api.api.iptables;

import io.patriot_framework.network.simulator.api.api.Controller;
import io.patriot_framework.network.simulator.api.api.iptables.chain.Chain;
import io.patriot_framework.network.simulator.api.api.iptables.rules.FilterRule;

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
