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

package io.patriot_framework.network.simulator.api.gateway;

import com.google.common.io.Files;
import io.patriot_framework.network_simulator.docker.container.DockerContainer;
import io.patriot_framework.network_simulator.docker.container.config.AppConfig;
import io.patriot_framework.network_simulator.docker.files.FileUtils;
import io.patriot_framework.network_simulator.docker.image.docker.DockerImage;
import io.patriot_framework.network_simulator.docker.image.docker.builder.DockerFileBuilder;
import io.patriot_framework.network_simulator.docker.manager.DockerManager;

import java.io.File;
import java.nio.file.Path;
import java.util.*;

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
            buildSimpleImage(name);
            createAndStart(name, tag);
            return apps.get(name).getAppConfig();
    }

    public AppConfig deploy(String name, List<String> resources) {
        buildImageWithResource(name, resources);
        createAndStart(name, tag);
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

    private void buildSimpleImage(String name) {

        File tmpDir = Files.createTempDir();
        buildDockerImage(name, tmpDir);

    }

    private void buildDockerImage(String name, File directory) {
        File dockerfile = new File(directory, "tempDockerfile");
        apps.get(name).getDockerFileBuilder().write(dockerfile.toPath());
        buildSimpleImage(new HashSet<>(Arrays.asList(tag)), dockerfile.toPath());
    }

    private void buildImageWithResource(String name, List<String> resourceNames) {

        File tmpDir = Files.createTempDir();
        copyResources(resourceNames, tmpDir);
        buildDockerImage(name, tmpDir);
    }

    private void copyResources(List<String> resourceNames, File directory) {
        ClassLoader classLoader = getClass().getClassLoader();
        FileUtils fileUtils = new FileUtils();

        for (String resource : resourceNames) {
            File res = new File(directory, resource);
            fileUtils.convertToFile(classLoader.getResourceAsStream(resource), res.toString());
        }
    }

    private void buildSimpleImage(Set<String> tags, Path dockerfile) {
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
