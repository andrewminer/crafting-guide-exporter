package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public class Dumper extends Worker {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        throw new IncompleteExtensionException("implementations must override at least one `dump` method");
    }

    public void dump(ModPackModel modPack, AsyncStep step) {
        this.dump(modPack);
        step.done();
    }

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected void work(ModPackModel modPack, AsyncStep step) {
        this.dump(modPack, step);
    }
}
