package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class ThermalFoundationModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("ThermalFoundation");
        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem("ThermalFoundation:Storage:12"));
        modPack.addMod(mod);
    }
}
