package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RobotGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel baseRobot = modPack.getItem(BASE_ROBOT_ID);
        if (baseRobot == null) return;

        List<ItemStack> subItems = new ArrayList<>();
        Item rawItem = baseRobot.getRawItemStack().getItem();
        rawItem.getSubItems(null, null, subItems);

        for (ItemStack rawSubItemStack : (List<ItemStack>) subItems) {
            ItemModel item = BuildCraftExtension.findOrCreateItem(rawSubItemStack, modPack);
            modPack.addItem(item);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_ROBOT_ID = "BuildCraft|Robotics:robot";
}
