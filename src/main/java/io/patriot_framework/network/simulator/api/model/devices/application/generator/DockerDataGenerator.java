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

package io.patriot_framework.network.simulator.api.model.devices.application.generator;

import io.patriot_framework.network.simulator.api.model.devices.Device;
import io.patriot_framework.network.simulator.api.model.network.Network;

import java.util.List;

public class DockerDataGenerator implements Device {

    private DeviceImage generatorImage;
    private String tag;
    private String name;
    private String ipAddress;
    Integer managementPort;
    List<Network> connectedNetworks;

    public DeviceImage getGeneratorImage() {
        return generatorImage;
    }

    public void setGeneratorImage(DeviceImage generatorImage) {
        this.generatorImage = generatorImage;
    }

    public DockerDataGenerator(DeviceImage generatorImage, String tag, String name) {
        this.generatorImage = generatorImage;
        this.tag = tag;
        this.name = name;
    }

    public DockerDataGenerator(String tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
    public Integer getManagementPort() {
        return managementPort;
    }

    @Override
    public void setManagementPort(Integer managementPort) {
        this.managementPort = managementPort;
    }

    @Override
    public String getCreator() {
        return "Docker";
    }
}
