package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BuildCraftExtension implements ExporterExtension {

    // Public Class Methods ////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemModel findOrCreateItem(ItemStack rawItemStack, ModPackModel modPack) {
        String itemId = Item.itemRegistry.getNameForObject(rawItemStack.getItem());
        if (!BASE_GATE_ID.equals(itemId)) return modPack.getItem(itemId);

        List<String> extraData = new ArrayList<>();
        rawItemStack.getItem().addInformation(rawItemStack, null, extraData, true);

        String extraChip = null;
        if (extraData.size() == 4 && extraData.get(2).indexOf("Expansions") != -1) {
            extraChip = extraData.get(3);
        }

        itemId = BASE_GATE_ID + ":" + CraftingGuideFileManager.slugify(rawItemStack.getDisplayName());
        if (extraChip != null) {
            itemId += ":" + CraftingGuideFileManager.slugify(extraChip);
        }

        ItemModel item = modPack.getItem(itemId);
        if (item == null) {
            item = new ItemModel(itemId, rawItemStack);

            if (extraChip != null) {
                item.setDisplayName(item.getDisplayName() + " (" + extraChip + ")");
            }
        }

        return item;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_GATE_ID = "BuildCraft|Transport:pipeGate";

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("buildcraft.GateGatherer");
        registry.registerWorker("buildcraft.RedstoneBoardGatherer");
        registry.registerWorker("buildcraft.RobotGatherer");
        registry.registerWorker("buildcraft.AssemblyTableRecipeGatherer");

        registry.registerWorker("buildcraft.BuildCraftModEditor");
        registry.registerWorker("buildcraft.BuildCraftGroupEditor");
    }
}
