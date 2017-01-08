package com.craftingguide.exporter.extensions.bigreactors;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class BigReactorsExtension implements ExporterExtension {

    public void register(Registry registry) {
        registry.registerWorker("bigreactors.ReprocessorRecipeGatherer");
        registry.registerWorker("bigreactors.BigReactorsMultiblockGatherer", Priority.LOW);

        registry.registerWorker("bigreactors.BigReactorsGroupEditor");
        registry.registerWorker("bigreactors.BigReactorsModEditor");
    }
}
