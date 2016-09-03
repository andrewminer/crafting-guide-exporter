package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public class Gatherer extends Worker {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        throw new IncompleteExtensionException("subclasses must override at least one `gather` method");
    }

    public void gather(ModPackModel modPack, AsyncStep step) {
        this.gather(modPack);
        step.done();
    }

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected void work(ModPackModel modPack, AsyncStep step) {
        this.gather(modPack, step);
    }
}
