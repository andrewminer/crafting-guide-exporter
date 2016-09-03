package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public abstract class Worker {

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected abstract void work(ModPackModel modPack, AsyncStep step);
}
