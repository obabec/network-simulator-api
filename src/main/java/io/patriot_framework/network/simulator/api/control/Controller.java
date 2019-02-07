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

package io.patriot_framework.network.simulator.api.control;

import io.patriot_framework.network.simulator.api.model.devices.Device;
import io.patriot_framework.network.simulator.api.model.devices.application.Application;
import io.patriot_framework.network.simulator.api.model.devices.router.Router;
import io.patriot_framework.network.simulator.api.model.network.Network;
import io.patriot_framework.network.simulator.api.model.routes.Route;

import java.io.File;


public interface Controller {
    void connectDeviceToNetwork(Device device, Network network);
    void stopDevice(Device router);
    void disconnectDevice(Network network);
    void deployApplication(Application device);
    void deployRouter(Router router);
    void destroyDevice(Device device);
    void createNetwork(Network network);
    void destroyNetwork(Network network);
    String deployDevice(Device device, String tag);
    String buildImage(File image);
}
