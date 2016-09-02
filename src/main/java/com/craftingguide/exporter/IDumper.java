package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public interface IDumper extends IWorker {

    public void dump(ModPackModel modPack);
}
