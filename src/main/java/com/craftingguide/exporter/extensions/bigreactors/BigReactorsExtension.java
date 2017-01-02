package com.craftingguide.exporter.extensions.bigreactors;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class BigReactorsExtension implements ExporterExtension {

    public void register(Registry registry) {
        registry.registerWorker("bigreactors.ReprocessorRecipeGatherer");
        registry.registerWorker("bigreactors.BigReactorsGroupEditor");
        registry.registerWorker("bigreactors.BigReactorsModEditor");
    }
}
