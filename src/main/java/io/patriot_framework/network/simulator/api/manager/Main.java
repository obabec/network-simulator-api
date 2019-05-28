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

package io.patriot_framework.network.simulator.api.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.patriot_framework.network.simulator.api.model.Topology;
import io.patriot_framework.network.simulator.api.model.devices.application.generator.DeviceImage;
import io.patriot_framework.network.simulator.api.model.devices.application.generator.DockerDataGenerator;
import io.patriot_framework.network.simulator.api.model.network.TopologyNetwork;
import io.patriot_framework.network.simulator.api.yaml.TopologyParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        /*List<DockerDataGenerator> dockerDataGenerators = new ArrayList<>();
        DeviceImage deviceImage = new DeviceImage();
        deviceImage.setYamlConfig(new File("/app"));
        DockerDataGenerator dockerDataGenerator = new DockerDataGenerator(deviceImage, "simpleTag", "namerino");
        TopologyNetwork topologyNetwork = new TopologyNetwork();
        topologyNetwork.setName("N1");
        dockerDataGenerator.getConnectedNetworks().add(topologyNetwork);
        dockerDataGenerators.add(dockerDataGenerator);
        dockerDataGenerators.add(dockerDataGenerator);

        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.writeValue(new File("/home/obabec/Desktop/testino.yaml"), dockerDataGenerators);*/

        TopologyParser topologyParser = new TopologyParser();
        Topology newTop = null;
        try {
            newTop = topologyParser.parseTopology(new File("/home/obabec/Desktop/topSer.yaml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
