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

package io.patriot_framework.network.simulator.api.model.devices.application;

import io.patriot_framework.network.simulator.api.model.devices.Device;
import io.patriot_framework.network.simulator.api.model.network.Network;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;

import java.util.List;


public class Application implements Device {

    private String name;
    private String ipAddress;
    private List<Network> connectedNetworks;
    private String creator;

    public String executeCommand(String[] commandWithArgs) {
        return null;
    }

    public Application(String name) {
        this.name = name;
    }

    public Application(String name, String creator) {
        this.name = name;
        this.creator = creator;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIPAddress() {
        return ipAddress;
    }

    @Override
    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public List<Network> getConnectedNetworks() {
        return connectedNetworks;
    }

    @Override
    public String getCreator() {
        return creator;
    }
}
