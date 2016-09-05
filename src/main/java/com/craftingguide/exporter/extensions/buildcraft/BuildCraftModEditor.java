package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.CompoundModModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class BuildCraftModEditor extends Editor {

    public void edit(ModPackModel modPack) {
        CompoundModModel mod = new CompoundModModel(BUILDCRAFT_ID, BUILDCRAFT_DISPLAY_NAME);
        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem(BUILDCRAFT_ICONIC_BLOCK));
        mod.setPrimaryChild(modPack.getMod(BUILDCRAFT_PRIMARY_MOD));

        for (ModModel childMod : modPack.getAllMods()) {
            if (childMod.getId().indexOf(mod.getId()) != 0) continue;
            mod.addChildMod(childMod);
        }

        if (mod.getAllChildMods().isEmpty()) return;
        modPack.addMod(mod);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BUILDCRAFT_DISPLAY_NAME = "BuildCraft";

    private static String BUILDCRAFT_ICONIC_BLOCK = "BuildCraft|Core:engineBlock:2";

    private static String BUILDCRAFT_ID = "BuildCraft";

    private static String BUILDCRAFT_PRIMARY_MOD = "BuildCraft|Core";
}
