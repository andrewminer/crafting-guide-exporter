package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class GateGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel baseGate = modPack.getItem(BASE_GATE_ID);
        if (baseGate == null) return;

        List<ItemStack> subItems = new ArrayList<>();
        Item rawItem = baseGate.getRawStack().getItem();
        rawItem.getSubItems(null, null, subItems);

        for (ItemStack rawSubItemStack : (List<ItemStack>) subItems) {
            List<String> extraData = new ArrayList<>();
            rawSubItemStack.getItem().addInformation(rawSubItemStack, null, extraData, true);

            String extraChip = null;
            if (extraData.size() == 4 && extraData.get(2).indexOf("Expansions") != -1) {
                extraChip = extraData.get(3);
            }

            String id = BASE_GATE_ID + ":" + CraftingGuideFileManager.slugify(rawSubItemStack.getDisplayName());
            if (extraChip != null) {
                id += ":" + CraftingGuideFileManager.slugify(extraChip);
            }

            ItemModel item = new ItemModel(id, rawSubItemStack);

            if (extraChip != null) {
                item.setDisplayName(item.getDisplayName() + " (" + extraChip + ")");
            }
            modPack.addItem(item);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_GATE_ID = "BuildCraft|Transport:pipeGate";

    private static String ROBOTICS_MOD_ID = "BuildCraft|Transport";
}
