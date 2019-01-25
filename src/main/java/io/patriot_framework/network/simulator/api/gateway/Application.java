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

import io.patriot_framework.network_simulator.docker.container.DockerContainer;
import io.patriot_framework.network_simulator.docker.container.config.AppConfig;
import io.patriot_framework.network_simulator.docker.image.docker.builder.DockerFileBuilder;

/**
 * The type Application.
 */
public class Application {
    private DockerFileBuilder dockerFileBuilder;
    private AppConfig appConfig;
    private DockerContainer dockerContainer;

    /**
     * Instantiates a new Application.
     *
     * @param dockerFileBuilder the docker file builder
     */
    public Application(DockerFileBuilder dockerFileBuilder) {
        this.dockerFileBuilder = dockerFileBuilder;
    }

    /**
     * Instantiates a new Application.
     *
     * @param dockerFileBuilder the docker file builder
     * @param appConfig         the app config
     */
    public Application(DockerFileBuilder dockerFileBuilder, AppConfig appConfig) {
        this.dockerFileBuilder = dockerFileBuilder;
        this.appConfig = appConfig;
    }

    /**
     * Instantiates a new Application.
     *
     * @param dockerFileBuilder the docker file builder
     * @param appConfig         the app config
     * @param dockerContainer   the docker container
     */
    public Application(DockerFileBuilder dockerFileBuilder, AppConfig appConfig, DockerContainer dockerContainer) {
        this.dockerFileBuilder = dockerFileBuilder;
        this.appConfig = appConfig;
        this.dockerContainer = dockerContainer;
    }

    /**
     * Gets docker file builder.
     *
     * @return the docker file builder
     */
    public DockerFileBuilder getDockerFileBuilder() {
        return dockerFileBuilder;
    }

    /**
     * Sets docker file builder.
     *
     * @param dockerFileBuilder the docker file builder
     */
    public void setDockerFileBuilder(DockerFileBuilder dockerFileBuilder) {
        this.dockerFileBuilder = dockerFileBuilder;
    }

    /**
     * Gets app config.
     *
     * @return the app config
     */
    public AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * Sets app config.
     *
     * @param appConfig the app config
     */
    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /**
     * Gets docker container.
     *
     * @return the docker container
     */
    public DockerContainer getDockerContainer() {
        return dockerContainer;
    }

    /**
     * Sets docker container.
     *
     * @param dockerContainer the docker container
     */
    public void setDockerContainer(DockerContainer dockerContainer) {
        this.dockerContainer = dockerContainer;
    }
}
