package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class CraftingGuideExtension implements ExporterExtension {

    // IExtension Methods //////////////////////////////////////////////////////////////////////////////////////////////

    public void register(Registry registry) {
        registry.registerDumper(new ItemIconDumper());
        registry.registerDumper(new ModDumper());
        registry.registerDumper(new ModIconDumper());
        registry.registerDumper(new ModVersionDumper());
        registry.registerDumper(new FurnaceFuelIconDumper(), Priority.LOW);
    }
}
