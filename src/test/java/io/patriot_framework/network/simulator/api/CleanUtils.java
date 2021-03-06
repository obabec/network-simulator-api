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

package io.patriot_framework.network.simulator.api;


import io.patriot_framework.network.simulator.api.model.Network;
import io.patriot_framework.network.simulator.api.model.Router;
import io.patriot_framework.network_simulator.docker.cleanup.Cleaner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CleanUtils {
    public void cleanUp(ArrayList<Network> topology, HashMap<String, Router> routers) {
        Cleaner cleaner = new Cleaner();
        List<String> networks = new ArrayList<>();
        List<String> rts = new ArrayList<>(routers.keySet());
        topology.forEach(network -> networks.add(network.getName()));
        cleaner.cleanUp(networks, rts);
    }

}
