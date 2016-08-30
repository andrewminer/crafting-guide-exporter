package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;

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
        registry.registerDumper(new ModVersionDumper(this.getFileManager()));
        registry.registerDumper(new ItemIconDumper(this.getFileManager()));
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager fileManager = null;
}
