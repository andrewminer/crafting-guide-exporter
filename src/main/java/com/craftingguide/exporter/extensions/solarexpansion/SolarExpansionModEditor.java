package com.craftingguide.exporter.extensions.solarexpansion;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class SolarExpansionModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("SolarExpansion");
        if (mod == null) return;

        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem("SolarExpansion:solarPanelUltimate"));
        mod.setUrl("https://mods.curse.com/mc-mods/minecraft/223583-solar-expansion");
    }
}
