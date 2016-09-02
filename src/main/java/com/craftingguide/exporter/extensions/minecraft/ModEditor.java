package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class ModEditor implements IEditor {

    @Override
    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("minecraft");
        mod.setAuthor("Mojang");
        mod.setDescription("The items and recipes from the vanilla game.");
        mod.setUrl("http://minecraft.net");
    }
}
