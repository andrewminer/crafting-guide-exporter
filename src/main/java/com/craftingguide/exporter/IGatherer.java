package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public interface IGatherer extends IWorker {

    public void gather(ModPackModel modPack);
}
