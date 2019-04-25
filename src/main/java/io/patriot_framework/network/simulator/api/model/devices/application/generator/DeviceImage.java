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

import java.io.File;

public class DeviceImage {
    File dockerFile;
    File javaEx;
    String tag;
    File yamlConfig;

    public DeviceImage() {
    }

    public DeviceImage(File dockerFile, File javaEx, String tag) {
        this.dockerFile = dockerFile;
        this.javaEx = javaEx;
        this.tag = tag;
    }

    public DeviceImage(File javaEx, String tag, File yamlConfig) {
        this.javaEx = javaEx;
        this.tag = tag;
        this.yamlConfig = yamlConfig;
    }

    public File getDockerFile() {
        return dockerFile;
    }

    public void setDockerFile(File dockerFile) {
        this.dockerFile = dockerFile;
    }

    public File getJavaEx() {
        return javaEx;
    }

    public void setJavaEx(File javaEx) {
        this.javaEx = javaEx;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public File getYamlConfig() {
        return yamlConfig;
    }

    public void setYamlConfig(File yamlConfig) {
        this.yamlConfig = yamlConfig;
    }
}
