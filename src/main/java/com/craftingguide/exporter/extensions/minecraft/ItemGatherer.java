package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        Set<String> ids = (Set<String>) Item.itemRegistry.getKeys();
        for (String id : ids) {
            Item item = (Item) Item.itemRegistry.getObject(id);

            if (item.getHasSubtypes()) {
                ArrayList<ItemStack> subItemStacks = new ArrayList<ItemStack>();
                item.getSubItems(item, null, subItemStacks);

                for (ItemStack stack : subItemStacks) {
                    String subTypeId = id + ":" + stack.getItemDamage();
                    modPack.addItem(new ItemModel(subTypeId, stack));
                }
            } else {
                ItemStack stack = new ItemStack(item, 1, 0);
                modPack.addItem(new ItemModel(id, stack));
            }
        }
    }
}
