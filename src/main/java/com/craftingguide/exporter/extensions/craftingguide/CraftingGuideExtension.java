package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class CraftingGuideExtension implements ExporterExtension {

    public CraftingGuideExtension() {
        this.fileManager = new CraftingGuideFileManager("./dumps");
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideFileManager getFileManager() {
        return this.fileManager;
    }

    // IExtension Methods //////////////////////////////////////////////////////////////////////////////////////////////

    public void register(Registry registry) {
        registry.registerDumper(new ItemIconDumper(this.getFileManager()));
        registry.registerDumper(new ModDumper(this.getFileManager()));
        registry.registerDumper(new ModIconDumper(this.getFileManager()));
        registry.registerDumper(new ModVersionDumper(this.getFileManager()));
        registry.registerDumper(new FurnaceFuelIconDumper(this.getFileManager()), Priority.LOW);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager fileManager = null;
}
