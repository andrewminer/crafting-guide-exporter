package com.craftingguide.exporter.extensions.enderstorage;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class EnderStorageExtension implements ExporterExtension {

    public void register(Registry registry) {
        registry.registerWorker("enderstorage.EnderStorageModEditor");
    }
}
