package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class ForgeExtension implements ExporterExtension {

    @Override
    public void register(Registry registry) {
        registry.registerGatherer(new ModGatherer(), Priority.HIGHEST);
        registry.registerGatherer(new OreDictionaryGatherer(), Priority.MEDIUM);
    }
}
