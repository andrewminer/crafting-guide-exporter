package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.Dumper;

public abstract class AbstractCraftingGuideDumper extends Dumper {

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideFileManager getFileManager() {
        return this.getConfig().getFileManager();
    }
}
