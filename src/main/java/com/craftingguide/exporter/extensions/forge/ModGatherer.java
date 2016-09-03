package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class ModGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        ModModel minecraft = new ModModel("minecraft", "Minecraft", Loader.MC_VERSION);
        modPack.addMod(minecraft);

        for (ModContainer rawMod : Loader.instance().getActiveModList()) {
            ModModel mod = new ModModel(rawMod);
            modPack.addMod(mod);
        }
    }
}
