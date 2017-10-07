package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
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
                    ItemModel itemModel = new ItemModel(subTypeId, stack);
                    if (needToAddItemToModModel) {
                        mod.addItem(itemModel);
                    }
                    modPack.addItem(itemModel);
                }
            } else {
                ItemStack stack = new ItemStack(item, 1, 0);
                ItemModel itemModel = new ItemModel(id, stack);
                if (needToAddItemToModModel) {
                    mod.addItem(itemModel);
                }
                modPack.addItem(itemModel);
            }
        }
    }
}
