package com.craftingguide.exporter.extensions.solarexpansion;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class SolarExpansionExtension implements ExporterExtension {

    @Override
    public void register(Registry registry) {
        registry.registerWorker("solarexpansion.SolarExpansionGroupEditor");
        registry.registerWorker("solarexpansion.SolarExpansionModEditor");
    }
}
