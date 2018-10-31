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

package com.redhat.patriot.network.simulator.api.gateway;

import com.redhat.patriot.network_simulator.example.container.DockerContainer;
import com.redhat.patriot.network_simulator.example.container.config.AppConfig;
import com.redhat.patriot.network_simulator.example.image.docker.DockerImage;
import com.redhat.patriot.network_simulator.example.image.docker.builder.DockerFileBuilder;
import com.redhat.patriot.network_simulator.example.manager.DockerManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * The type App manager.
 */
public class AppManager {
    private String tag;
    private DockerManager dockerManager = new DockerManager();
    private HashMap<String, Application> apps = new HashMap<>();

    /**
     * Deploy application.
     *
     * @param name the name
     * @return the app config
     */
    public AppConfig deploy(String name) {
            buildImage(name);
            createAndStart(name, tag);
            dockerManager.runCommand(apps.get(name).getDockerContainer(), apps.get(name)
                    .getAppConfig().getStartCommand());
            return apps.get(name).getAppConfig();
    }

    /**
     * Destroy application, including docker container.
     *
     * @param name the name
     */
    public void destroy(String name) {
        dockerManager.destroyContainer(apps.get(name).getDockerContainer());
    }

    private void buildImage(String name) {

        try {
            Path tmpDir = Files.createTempDirectory(Paths.get("/tmp"), "dockerfiles");
            Path dockerfile = Files.createTempFile(tmpDir, "tempDockerfile", "");
            apps.get(name).getDockerFileBuilder().write(dockerfile);
            buildImage(new HashSet<>(Arrays.asList(tag)), dockerfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets start command.
     *
     * @param name    the name
     * @param command the command
     */
    public void setStartCommand(String name, String command) {
        apps.get(name).getAppConfig().setStartCommand(command);
    }

    private void buildImage(Set<String> tags, Path dockerfile) {
        DockerImage dockerImage = new DockerImage(dockerManager);
        dockerImage.buildImage(tags, dockerfile.toAbsolutePath().toString());
    }

    private DockerContainer createAndStart(String name, String tag) {
        DockerContainer appCont = (DockerContainer) dockerManager.createContainer(name, tag);
        dockerManager.startContainer(appCont);
        apps.get(name).setDockerContainer(appCont);
        AppConfig appConfig = apps.get(name).getAppConfig();
        appConfig.setIPAdd(dockerManager.findIpAddress(apps.get(name).getDockerContainer()));
        appConfig.setStatus("running");
        apps.get(name).setAppConfig(appConfig);
        return appCont;
    }

    /**
     * Create new dockerfileBuilder for application.
     *
     * @param name the name
     * @return the docker file builder
     */
    public DockerFileBuilder newApp(String name) {
        DockerFileBuilder dockerFileBuilder = new DockerFileBuilder();
        apps.put(name, new Application(dockerFileBuilder, new AppConfig(name)));
        return dockerFileBuilder;
    }

    /**
     * Execute command in application container
     *
     * @param appConfig the app config
     * @param command   the command
     */
    public void executeCommand(AppConfig appConfig, String command) {
        dockerManager.runCommand(apps.get(appConfig.getName()).getDockerContainer(), command);
    }

    /**
     * Sets container tag.
     *
     * @param tag the tag
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
