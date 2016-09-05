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
        Item rawItem = baseRobot.getRawStack().getItem();
        rawItem.getSubItems(null, null, subItems);

        for (ItemStack rawSubItemStack : (List<ItemStack>) subItems) {
            String robotType = null;
            List<String> extraData = new ArrayList<>();
            rawSubItemStack.getItem().addInformation(rawSubItemStack, null, extraData, true);
            if (extraData.size() > 0) {
                robotType = ((String) extraData.get(0)).substring(2);
            }

            String id = BASE_ROBOT_ID;
            if (robotType != null) {
                id += ":" + robotType;
            }

            ItemModel item = new ItemModel(id, rawSubItemStack);
            if (robotType != null) {
                item.setDisplayName(item.getDisplayName() + " (" + robotType + ")");
            }

            modPack.addItem(item);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_ROBOT_ID = "BuildCraft|Robotics:robot";
}
