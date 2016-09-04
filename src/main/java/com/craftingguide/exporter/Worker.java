package com.craftingguide.exporter;

import com.craftingguide.CraftingGuideConfig;
import com.craftingguide.exporter.models.ModPackModel;

public abstract class Worker {

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideConfig getConfig() {
        if (this.config == null) throw new IllegalStateException("this worker has not been registered yet");
        return this.config;
    }

    public void setConfig(CraftingGuideConfig config) {
        this.config = config;
    }

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected abstract void work(ModPackModel modPack, AsyncStep step);

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideConfig config = null;
}
