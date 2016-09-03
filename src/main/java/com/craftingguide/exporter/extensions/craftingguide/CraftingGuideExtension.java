package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;
import com.craftingguide.exporter.IRegistry.Priority;

public class CraftingGuideExtension implements IExtension {

    public CraftingGuideExtension() {
        this.fileManager = new CraftingGuideFileManager("./dumps");
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideFileManager getFileManager() {
        return this.fileManager;
    }

    // IExtension Methods //////////////////////////////////////////////////////////////////////////////////////////////

    public void register(IRegistry registry) {
        registry.registerDumper(new ItemIconDumper(this.getFileManager()));
        registry.registerDumper(new ModDumper(this.getFileManager()));
        registry.registerDumper(new ModVersionDumper(this.getFileManager()));
        registry.registerDumper(new FurnaceFuelIconDumper(this.getFileManager()), Priority.LOW);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager fileManager = null;
}
