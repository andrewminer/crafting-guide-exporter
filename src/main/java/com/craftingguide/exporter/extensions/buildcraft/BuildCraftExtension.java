package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.Slugifier;
import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;

public class BuildCraftExtension implements ExporterExtension {

    // Public Class Methods ////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemModel findOrCreateItem(ItemStack rawItemStack, ModPackModel modPack) {
        ItemModel item = modPack.getItem(rawItemStack);
        if (item.getId().equals(BASE_BOARD_ID)) return findOrCreateBoard(rawItemStack, modPack);
        if (item.getId().equals(BASE_GATE_ID)) return findOrCreateGate(rawItemStack, modPack);
        if (item.getId().equals(BASE_ROBOT_ID)) return findOrCreateRobot(rawItemStack, modPack);
        return item;
    }

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("buildcraft.GateGatherer");
        registry.registerWorker("buildcraft.RedstoneBoardGatherer");
        registry.registerWorker("buildcraft.RobotGatherer");

        registry.registerWorker("buildcraft.AssemblyTableRecipeGatherer", Priority.LOW);
        registry.registerWorker("buildcraft.IntegrationTableRecipeGatherer", Priority.LOW);
        registry.registerWorker("buildcraft.RefineryRecipeGatherer", Priority.LOW);
        registry.registerWorker("buildcraft.ProgrammingTableRecipeGatherer", Priority.LOW);

        registry.registerWorker("buildcraft.BuildCraftModEditor");
        registry.registerWorker("buildcraft.BuildCraftGroupEditor");
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BASE_BOARD_ID = "BuildCraft|Robotics:redstone_board";

    private static String BASE_GATE_ID = "BuildCraft|Transport:pipeGate";

    private static String BASE_ROBOT_ID = "BuildCraft|Robotics:robot";

    private static Slugifier SLUGIFIER = new Slugifier();

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static ItemModel findOrCreateBoard(ItemStack itemStack, ModPackModel modPack) {
        String boardType = null;
        List<String> extraData = new ArrayList<>();
        itemStack.getItem().addInformation(itemStack, null, extraData, true);
        if (extraData.size() > 0) {
            boardType = ((String) extraData.get(0)).substring(2);
        }

        String id = BASE_BOARD_ID;
        if (boardType != null) {
            id += ":" + boardType;
        }

        ItemModel item = modPack.getItem(id);
        if (item == null) {
            item = new ItemModel(id, itemStack);

            if (boardType != null) {
                item.setDisplayName(item.getDisplayName() + " (" + boardType + ")");
            }
        }

        return item;
    }

    private static ItemModel findOrCreateGate(ItemStack rawItemStack, ModPackModel modPack) {
        List<String> extraData = new ArrayList<>();
        rawItemStack.getItem().addInformation(rawItemStack, null, extraData, true);

        String extraChip = null;
        if (extraData.size() == 4 && extraData.get(2).indexOf("Expansions") != -1) {
            extraChip = extraData.get(3);
        }

        String itemId = BASE_GATE_ID + ":" + SLUGIFIER.slugify(rawItemStack.getDisplayName());
        if (extraChip != null) {
            itemId += ":" + SLUGIFIER.slugify(extraChip);
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

    private static ItemModel findOrCreateRobot(ItemStack itemStack, ModPackModel modPack) {
        String robotType = null;
        List<String> extraData = new ArrayList<>();
        itemStack.getItem().addInformation(itemStack, null, extraData, true);
        if (extraData.size() > 0) {
            robotType = ((String) extraData.get(0)).substring(2);
        }

        String id = BASE_ROBOT_ID;
        if (robotType != null) {
            id += ":" + robotType;
        }

        ItemModel item = modPack.getItem(id);
        if (item == null) {
            item = new ItemModel(id, itemStack);

            if (robotType != null) {
                item.setDisplayName(item.getDisplayName() + " (" + robotType + ")");
            }
        }

        return item;
    }
}
