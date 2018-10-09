package com.redhat.patriot.network_controll.api.iproute;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.patriot.network_controll.api.Controller;
import com.redhat.patriot.network_controll.model.routes.Route;

import java.io.IOException;
import java.util.List;

/**
 * The type Route controller.
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
    public List<Route> getRouters() {
        String path = "/iproutes";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String input = executeHttpRequest(path, "GET");
            return objectMapper.readValue(input, new TypeReference<List<Route>>(){});
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
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
        String path = "/iproutes/default/" + defaultGW.getrNetworkInterface();
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
            String input = executeHttpRequest("/iproutes/networkInterfaces", "GET");
            List<NetworkInterface> networkInterfaces = objectMapper.readValue(input, new TypeReference<List<NetworkInterface>>(){});
            return networkInterfaces;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
