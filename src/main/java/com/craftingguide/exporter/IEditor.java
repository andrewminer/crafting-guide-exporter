package com.craftingguide.exporter;

import com.craftingguide.exporter.models.ModPackModel;

public interface IEditor extends IWorker {

    public void edit(ModPackModel modPack);
}
