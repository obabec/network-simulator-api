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

package io.patriot_framework.network.simulator.api.model.network;

import io.patriot_framework.network.simulator.api.model.EnvironmentPart;

public abstract class Network implements EnvironmentPart {

    private String name;
    private String id;
    private String ipAddress;
    private int mask;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIPAddress() {
        return ipAddress;
    }
    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public Integer getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }
}
