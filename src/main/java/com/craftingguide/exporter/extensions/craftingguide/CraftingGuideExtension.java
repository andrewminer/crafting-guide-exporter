package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class CraftingGuideExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    public void register(Registry registry) {
        registry.registerWorker("craftingguide.ItemIconDumper");
        registry.registerWorker("craftingguide.ModDumper");
        registry.registerWorker("craftingguide.ModIconDumper");
        registry.registerWorker("craftingguide.ModVersionDumper");
        registry.registerWorker("craftingguide.FurnaceFuelIconDumper", Priority.LOW);
    }
}
