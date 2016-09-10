package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RedstoneBoardGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel baseBoard = modPack.getItem(BASE_BOARD_ID);
        if (baseBoard == null) return;

        List<ItemStack> subItems = new ArrayList<>();
        Item rawItem = baseBoard.getRawItemStack().getItem();
        rawItem.getSubItems(null, null, subItems);

        for (ItemStack rawSubItemStack : (List<ItemStack>) subItems) {
            ItemModel item = BuildCraftExtension.findOrCreateItem(rawSubItemStack, modPack);
            modPack.addItem(item);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_BOARD_ID = "BuildCraft|Robotics:redstone_board";

    private static String ROBOTICS_MOD_ID = "BuildCraft|Robotics";
}
