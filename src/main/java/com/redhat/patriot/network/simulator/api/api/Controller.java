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

package com.redhat.patriot.network.simulator.api.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The type Controller provides Http request execution.
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
            try {
                if (isAccessible()) {
                    connection.setRequestMethod(method);
                    connection.connect();
                    StringBuilder sb = new StringBuilder();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String output;
                        while ((output = br.readLine()) != null) {
                            sb.append(output);
                        }
                    }
                    return sb.toString();
                } else {
                    return null;
                }
            } finally {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return String.valueOf(HttpURLConnection.HTTP_INTERNAL_ERROR);
        }
    }
    private boolean isAccessible() {

        while (true) {
            try {
                URL url = new URL("http", ip, port, "/");
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        return true;
                    } else {
                        Thread.sleep(200);
                    }
                } finally {
                    connection.disconnect();
                }
            } catch (Exception e) {
            }
        }
    }
}
