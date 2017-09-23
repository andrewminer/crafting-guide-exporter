package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Set;

import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;

public class ItemGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        Set<String> ids = (Set<String>) Item.itemRegistry.getKeys();
        for (String id : ids) {
            Item item = (Item) Item.itemRegistry.getObject(id);

            ModContainer rawMod = GameData.findModOwner(GameData.getItemRegistry().getNameForObject(item));
            ModModel mod = rawMod == null ? modPack.getMod("minecraft") : modPack.getMod(rawMod.getModId());
            //TODO: Buildcraft items are gotten 2 times
            boolean needToAddItemToModModel = true;
            for (ItemModel itemModel : mod.getAllItems()) {
            	if (itemModel.getRawItemStack().getItem() == item) {
            		needToAddItemToModModel = false;
            	}
            }
            if (item.getHasSubtypes()) {
                ArrayList<ItemStack> subItemStacks = new ArrayList<ItemStack>();
                item.getSubItems(item, null, subItemStacks);

                for (ItemStack stack : subItemStacks) {
                    String subTypeId = id + ":" + stack.getItemDamage();
                    if (needToAddItemToModModel) {
                    	mod.addItem(new ItemModel(subTypeId, stack));
                    }
                    modPack.addItem(new ItemModel(subTypeId, stack));
                }
            } else {
                ItemStack stack = new ItemStack(item, 1, 0);
                if (needToAddItemToModModel) {
                	mod.addItem(new ItemModel(id, stack));
                }
                modPack.addItem(new ItemModel(id, stack));
            }
        }
    }
}
