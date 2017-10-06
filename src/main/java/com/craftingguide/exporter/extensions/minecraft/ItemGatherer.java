package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import codechicken.nei.guihook.GuiContainerManager;

public class ItemGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        Set<String> ids = (Set<String>) Item.itemRegistry.getKeys();
        for (String id : ids) {
            Item item = (Item) Item.itemRegistry.getObject(id);
            if (item.getHasSubtypes()) {
                ArrayList<ItemStack> subItemStacks = new ArrayList<ItemStack>();
                item.getSubItems(item, null, subItemStacks);
                
                if (subItemStacks.isEmpty()) {
                    // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2fNyMLI
                    // BEGIN NEI

                    HashSet<String> damageIconSet = new HashSet<String>();
                    for (int damage = 0; damage < 16; damage++) {
                        ItemStack itemstack = new ItemStack(item, 1, damage);
                        IIcon icon = item.getIconIndex(itemstack);
                        String name = GuiContainerManager.concatenatedDisplayName(itemstack, false);
                        String s = name + "@" + (icon == null ? 0 : icon.hashCode());
                        if (!damageIconSet.contains(s)) {
                            damageIconSet.add(s);
                            subItemStacks.add(itemstack);
                        }
                    }

                    // END NEI
                }

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
