package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

public class MinecraftModEditor extends Editor {

    @Override
    public void edit(ModPackModel modPack) {
        ModModel mod = modPack.getMod("minecraft");
        mod.setAuthor("Mojang");
        mod.setDescription("The items and recipes from the vanilla game.");
        mod.setEnabled(true);
        mod.setIconicBlock(modPack.getItem("minecraft:grass"));
        mod.setUrl("http://minecraft.net");
    }
}
