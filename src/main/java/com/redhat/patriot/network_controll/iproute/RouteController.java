package com.redhat.patriot.network_controll.iproute;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.redhat.patriot.network_controll.Controller;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class RouteController  extends Controller {
    public RouteController(String ip, Integer port) {
        super(ip, port);
    }

    public String addRoute(Route route) {
        String path = "/iproutes/" + route.toPath();
        return executeHttpRequest(path, "PUT");
    }

    public String deleteRoute(Route route) {
        String path = "/iproutes/" + route.toPath();
        return executeHttpRequest(path, "DELETE");
    }

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

    public String addDefaultGW(Route defaultGW) {
        String path = "/iproutes/default/" + defaultGW.getGateway();
        return executeHttpRequest(path, "PUT");
    }
    public String delDefaultGW(Route defaultGW ) {
        String path = "/iproutes/default/" + defaultGW.getGateway();
        return executeHttpRequest(path, "DELETE");
    }

    public List<Interface> getInterfaces() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String input = executeHttpRequest("/iproutes/interfaces", "GET");
            List<Interface> interfaces = objectMapper.readValue(input, new TypeReference<List<Interface>>(){});
            return interfaces;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
