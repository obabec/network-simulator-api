/*
 * Copyright 2019 Patriot project
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

package io.patriot_framework.network.simulator.api.api.iproute;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.patriot_framework.network.simulator.api.api.Controller;
import io.patriot_framework.network.simulator.api.model.routes.Route;

import java.io.IOException;
import java.util.List;

/**
 * Controller for ip tables api.
 * Implementing Controller. Is used for work with http ip-route rest api running in container.
 *
 */
public class RouteController  extends Controller {

    /**
     * Instantiates a new Route controller.
     *
     * @param ip   the ip
     * @param port the port
     */
    public RouteController(String ip, Integer port) {
        super(ip, port);
    }

    /**
     * Add route string.
     *
     * @param route the route
     * @return the string
     */
    public String addRoute(Route route) {
        String path = "/iproutes/" + route.toPath();
        return executeHttpRequest(path, "PUT");
    }

    /**
     * Delete route string.
     *
     * @param route the route
     * @return the string
     */
    public String deleteRoute(Route route) {
        String path = "/iproutes/" + route.toPath();
        return executeHttpRequest(path, "DELETE");
    }

    /**
     * Gets routers.
     *
     * @return the routers
     */
    public List<Route> getRoutes() {
        String path = "/iproutes";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String input = executeHttpRequest(path, "GET");
            return objectMapper.readValue(input, new TypeReference<List<Route>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Add default gw string.
     *
     * @param defaultGW the default gw
     * @return the string
     */
    public String addDefaultGW(Route defaultGW) {
        String path = "/iproutes/default/" + defaultGW.getrNetworkInterface().getIp();
        return executeHttpRequest(path, "PUT");
    }

    /**
     * Del default gw string.
     *
     * @param defaultGW the default gw
     * @return the string
     */
    public String delDefaultGW(Route defaultGW ) {
        String path = "/iproutes/default/" + defaultGW.getrNetworkInterface().getIp();
        return executeHttpRequest(path, "DELETE");
    }

    /**
     * Gets interfaces.
     *
     * @return the interfaces
     */
    public List<NetworkInterface> getInterfaces() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String input = executeHttpRequest("/iproutes/interfaces", "GET");
            List<NetworkInterface> networkInterfaces =
                    objectMapper.readValue(input, new TypeReference<List<NetworkInterface>>(){});
            return networkInterfaces;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
