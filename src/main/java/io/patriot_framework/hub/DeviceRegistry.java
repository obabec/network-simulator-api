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

package io.patriot_framework.hub;

import io.patriot_framework.generator.device.Unit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implements registry of devices deployed within simulated environment
 * Currently can only hold access to the plain references of generators
 * but in the future will be used as accessor to all deployed devices
 * within simulated environment
 */
public class DeviceRegistry {

    private Map<UUID, Unit> devices = new HashMap<>();

    /**
     * registers device to registry
     * @param device device to be stored
     * @return true if device is not already in registry, false otherwise
     */
    public boolean putDevice(Unit device) {
        if (device == null) {
            throw new IllegalArgumentException("device is null");
        }
        if (devices.containsKey(device.getUUID())) {
            return false;
        }
        devices.put(device.getUUID(), device);
        return true;
    }

    /**
     * Removes device from registry
     * @param id uuid of device to be deleted
     * @return true when device was deleted, false otherwise
     */
    public boolean removeDevice(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }
        if (!devices.containsKey(id)) {
            return false;
        }
        devices.remove(id);
        return true;
    }

    /**
     * Searches for device in registry
     * @param id uuid of device in registry
     * @return Unit object if it is found, null otherwise
     */
    public Unit getDevice(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }
        return devices.get(id);
    }

    /**
     * Get stream of all devices
     * @return all devices in stream
     */
    public Collection<Unit> getAllDevices()
    {
        return devices.values();
    }

    /**
     * Returns stream of devices with specified label
     * @param label searched label
     * @return Units with concrete label
     */
    public Collection<Unit> getDevicesByLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Label is null");
        }
        return devices.values().
                stream().filter(u -> label.equals(u.getLabel())).
                collect(Collectors.toSet());
    }
}
