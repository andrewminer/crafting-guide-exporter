package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public class Editor extends Worker {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        throw new IncompleteExtensionException("implementations must override at least one `edit` method");
    }

    public void edit(ModPackModel modPack, AsyncStep step) {
        this.edit(modPack);
        step.done();
    }

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected void work(ModPackModel modPack, AsyncStep step) {
        this.edit(modPack, step);
    }
}
