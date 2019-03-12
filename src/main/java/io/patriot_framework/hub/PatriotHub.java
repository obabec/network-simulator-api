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

package io.patriot_framework.hub;


import io.patriot_framework.network.simulator.api.manager.Manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Entry interface to the Patriot Framework
 * It contains all controlling APIs that are available to use
 *
 * The class implements singleton design pattern, which means there is
 * always at most one PatriotHub within one JVM process
 */
public class PatriotHub {

    public static Logger log = Logger.getLogger(PatriotHub.class.toString());

    private static PatriotHub singleton = null;

    private Manager manager;
    private DeviceRegistry registry;
    private Properties properties;

    public DeviceRegistry getRegistry() {
        return registry;
    }

    public void setRegistry(DeviceRegistry registry) {
        this.registry = registry;
    }

    /**
     * Private constructor to build singleton instance
     * @throws PropertiesNotLoadedException thrown when property io.patriot_framework.router is not defined
     */
    private PatriotHub() throws PropertiesNotLoadedException  {
        InputStream reader = null;
        properties = new Properties();
        IOException exception = null;
        try {
            reader = new FileInputStream("patriot.properties");
            properties.load(reader);
        } catch (IOException e) {
            exception = e;
        }

        if (exception != null) {
            properties = System.getProperties();
            if (!properties.containsKey("io.patriot_framework.router")) {
                throw new PropertiesNotLoadedException("properties not loaded", exception);
            }
        }

        manager = new Manager(properties.getProperty("io.patriot_framework.router"));
        if (properties.containsKey("io.patriot_framework.monitoring.addr")) {
            manager.setMonitoring(properties.getProperty("io.patriot_framework.monitoring.addr"),
                    Integer.valueOf(properties.getProperty("io.patriot_framework.monitoring.port")));
        }

        registry = new DeviceRegistry();
    }

    /**
     * Singleton accessor
     * @return PatriotHub instance, this cannot return null
     * @throws PropertiesNotLoadedException when creation of instance fails due to missing property
     */
    public static PatriotHub getInstance() throws PropertiesNotLoadedException{
        if (singleton == null) {
            singleton = new PatriotHub();
        }
        return singleton;
    }

    /**
     * Accessor to the simulators NetworkManager, which is main controlling interface for simulated
     * network
     * @return current NetworkManager
     */
    public Manager getManager()
    {
        return manager;
    }

}
