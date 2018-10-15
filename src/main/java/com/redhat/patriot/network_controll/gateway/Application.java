package com.redhat.patriot.network_controll.gateway;

import com.redhat.patriot.network_simulator.example.container.DockerContainer;
import com.redhat.patriot.network_simulator.example.container.config.AppConfig;
import com.redhat.patriot.network_simulator.example.image.docker.builder.DockerFileBuilder;

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
