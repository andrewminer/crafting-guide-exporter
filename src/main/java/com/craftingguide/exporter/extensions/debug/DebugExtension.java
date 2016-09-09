package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class DebugExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("debug.FluidDumper");
        registry.registerWorker("debug.ItemDumper");
        registry.registerWorker("debug.ModDumper");
        registry.registerWorker("debug.OreDictionaryDumper");
    }
}
