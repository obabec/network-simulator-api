package com.redhat.patriot.network_controll;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class Controller {
    private String ip;
    private Integer port;

    public String getIp() {
        return ip;
    }

    public Integer getPort() {
        return port;
    }

    public Controller(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

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
