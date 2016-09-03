package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.Dumper;

public abstract class AbstractCraftingGuideDumper extends Dumper {

    public AbstractCraftingGuideDumper(CraftingGuideFileManager fileManager) {
        this.setFileManager(fileManager);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideFileManager getFileManager() {
        return this._fileManager;
    }

    public void setFileManager(CraftingGuideFileManager newFileManager) {
        if (newFileManager == null) throw new IllegalArgumentException("fileManager cannot be null");
        this._fileManager = newFileManager;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager _fileManager = null;
}
