package com.craftingguide.exporter.extensions.enderstorage;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class EnderStorageModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("EnderStorage");
        if (mod == null) return;

        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem("minecraft:stone"));
    }
}
