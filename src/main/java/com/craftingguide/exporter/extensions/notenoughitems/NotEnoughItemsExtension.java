package com.craftingguide.exporter.extensions.notenoughitems;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class NotEnoughItemsExtension implements ExporterExtension {

    @Override
    public void register(Registry registry) {
        registry.registerWorker("notenoughitems.OtherRecipeGatherer", Priority.LOWEST);
    }

}
