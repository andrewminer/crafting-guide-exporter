package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ModPackModel;

public abstract class AbstractCraftingGuideDumper implements IDumper {

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

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public abstract void dump(ModPackModel modPack);

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager _fileManager = null;
}
