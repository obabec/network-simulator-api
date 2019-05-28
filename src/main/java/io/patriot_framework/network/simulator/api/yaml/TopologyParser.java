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

package io.patriot_framework.network.simulator.api.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.application.generator.DockerDataGenerator;
import io.patriot_framework.network.simulator.api.model.devices.router.RouterImpl;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;
import io.patriot_framework.network.simulator.api.model.routes.CalcRoute;
import io.patriot_framework.network.simulator.api.model.routes.NextHop;

import java.io.File;
import java.io.IOException;

public class TopologyParser {

    public Topology parseTopology(File yaml) throws IOException {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.findAndRegisterModules();
        Topology topology = mapper.readValue(yaml, Topology.class);
        completeRouteList(topology);
        return topology;
    }

    public void serializeTopology(Topology topology, File destYaml) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(destYaml, topology);
    }

    /* TODO: Predpoklad je, ze uzivatel to bude zadavat postupne, tak ze u posledni site uz nebude davat zadnou dalsi */

    private void completeRouteList(Topology topology) {

        for(int i = 0; i < topology.getNetworks().size(); i++) {
            NextHop nextHop = new NextHop(null, i);
            CalcRoute calcRoute = new CalcRoute(nextHop, null);
            TopologyNetwork cNetwork =  topology.getNetworks().get(i);
            cNetwork.getCalcRoutes().add(i, calcRoute);

            for (int y = i + 1; y < cNetwork.getCalcRoutes().size(); y++) {
                CalcRoute cR = cNetwork.getCalcRoutes().get(y);
                RouterImpl cRouter = cR.getNextHop().getRouter();
                if (cRouter != null) {
                    RouterImpl router = topology.findRouterByName(cRouter.getName());
                    cR.getNextHop().setRouter(router);
                }
                NextHop nextHop1 = new NextHop(cR.getNextHop().getRouter(), i);
                CalcRoute nR = new CalcRoute(nextHop1, cR.getCost());
                topology.getNetworks().get(cR.getNextHop().getNetwork()).getCalcRoutes().add(i, nR);
            }
        }
    }
}
