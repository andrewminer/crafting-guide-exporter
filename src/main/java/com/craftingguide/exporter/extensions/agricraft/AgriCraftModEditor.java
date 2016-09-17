package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class AgriCraftModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("AgriCraft");
        if (mod == null) return;

        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem("AgriCraft:seedAnalyzer"));
    }
}
