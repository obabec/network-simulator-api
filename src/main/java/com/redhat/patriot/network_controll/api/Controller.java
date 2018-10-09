package com.redhat.patriot.network_controll.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The type Controller.
 */
public abstract class Controller {
    private String ip;
    private Integer port;

    /**
     * Gets ip.
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Instantiates a new Controller.
     *
     * @param ip   the ip
     * @param port the port
     */
    public Controller(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Execute http request string.
     *
     * @param path   the path
     * @param method the method
     * @return the string
     */
    public String executeHttpRequest(String path, String method) {
        try {
            URL chainUrl = new URL("http", ip, port, path);
            HttpURLConnection connection = (HttpURLConnection) chainUrl.openConnection();
            connection.setRequestMethod(method);
            StringBuilder sb = new StringBuilder();
            if (connection.getResponseCode() == 200) {
                try(BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String output;
                    while ((output = br.readLine()) != null) {
                        sb.append(output);
                    }
                }
            }

            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "500";
        }
    }
}
