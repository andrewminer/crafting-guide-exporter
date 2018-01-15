package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

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
                        String name = concatenatedDisplayName(itemstack);
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
    
    // The following has been borrowed from ChickenBones' NEI code at http://bit.ly/2EDkszD and http://bit.ly/2AXj5t7
    // BEGIN NEI
    
    public String concatenatedDisplayName(ItemStack itemstack) {
        List<String> list = itemDisplayNameMultiline(itemstack);
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String name : list) {
            if (first) {
                first = false;
            } else {
                sb.append("#");
            }
            sb.append(name);
        }
        return EnumChatFormatting.getTextWithoutFormattingCodes(sb.toString());
    }
    
    public List<String> itemDisplayNameMultiline(ItemStack stack) {
        List<String> namelist = null;
        try {
            namelist = stack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
        } catch (Throwable ignored) {}

        if (namelist == null)
            namelist = new ArrayList<String>();

        if (namelist.size() == 0)
            namelist.add("Unnamed");

        if (namelist.get(0) == null || namelist.get(0).equals(""))
            namelist.set(0, "Unnamed");

        namelist.set(0, stack.getRarity().rarityColor.toString() + namelist.get(0));
        for (int i = 1; i < namelist.size(); i++)
            namelist.set(i, "\u00a77" + namelist.get(i));

        return namelist;
    }
    
    //END NEI
    
}
