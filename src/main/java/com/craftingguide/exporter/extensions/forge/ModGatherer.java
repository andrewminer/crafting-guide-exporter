package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ModGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        ModModel minecraft = new ModModel("minecraft", "Minecraft", Loader.MC_VERSION);
        modPack.addMod(minecraft);

        for (ModContainer rawMod : Loader.instance().getActiveModList()) {
            //TODO: find some way to get a block to represent the mod
            //TODO: find some way to group the items
            ModModel mod = new ModModel(rawMod);
            
            mod.setAuthor(mod.getRawMod().getMetadata().getAuthorList());
            mod.setDescription(mod.getRawMod().getMetadata().description);
            ItemModel block = null;
            for (Object itemObject : GameData.getItemRegistry()) {
                Item item = (Item) itemObject;
                if (GameData.getItemRegistry().getNameForObject(item).indexOf(rawMod.getModId()) == 0) {
                    if (item instanceof ItemBlock) {
                        block = new ItemModel(GameData.getItemRegistry().getNameForObject(item), new ItemStack(item));
                        break;
                    }
                }
            }
            mod.setIconicBlock(block);
            mod.setUrl(mod.getUrl());
            mod.setEnabled(true);
            modPack.addMod(mod);
        }
    }
}
