package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class ProgrammingTableRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel programmingTable = modPack.getItem(PROGRAMMING_TABLE_ID);
        ItemModel redstoneBoard = modPack.getItem(REDSTONE_BOARD_ID);

        for (ItemModel item : modPack.getAllItems()) {
            if (item.getDisplayName().indexOf(REDSTONE_BOARD_BASE_NAME) == -1) continue;
            if (item.getId().equals(REDSTONE_BOARD_ID)) continue;

            RecipeModel recipe = new RecipeModel(new ItemStackModel(item, 1));
            recipe.setInputAt(1, 1, new ItemStackModel(redstoneBoard, 1));
            recipe.addTool(programmingTable);
            modPack.addRecipe(recipe);
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static String PROGRAMMING_TABLE_ID = "BuildCraft|Silicon:laserTableBlock:4";

    private static String REDSTONE_BOARD_BASE_NAME = "Redstone Board";

    private static String REDSTONE_BOARD_ID = "BuildCraft|Robotics:redstone_board";
}
