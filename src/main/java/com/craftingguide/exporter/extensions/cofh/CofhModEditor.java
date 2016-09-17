package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class CofhModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("RedstoneArsenal");
        if (mod != null) {
            mod.setEnabled(true);
            mod.setIconicBlock(modPack.getItem("RedstoneArsenal:Storage:0"));
        }

        mod = modPack.getMod("ThermalDynamics");
        if (mod != null) {
            mod.setEnabled(true);
            mod.setIconicBlock(modPack.getItem("ThermalDynamics:ThermalDynamics_32:0"));
        }

        mod = modPack.getMod("ThermalExpansion");
        if (mod != null) {
            mod.setEnabled(true);
            mod.setIconicBlock(modPack.getItem("ThermalExpansion:Machine:1"));
        }

        mod = modPack.getMod("ThermalFoundation");
        if (mod != null) {
            mod.setEnabled(true);
            mod.setIconicBlock(modPack.getItem("ThermalFoundation:Storage:12"));
        }
    }
}
