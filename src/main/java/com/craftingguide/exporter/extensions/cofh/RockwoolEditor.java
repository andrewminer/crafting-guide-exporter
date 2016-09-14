package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;

public class RockwoolEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        ItemModel craftingTable = modPack.getItem(CRAFTING_TABLE_ID);
        ItemStackModel defaultRockwoolStack = new ItemStackModel(modPack.getItem(LIGHT_GRAY_ROCKWOOL), 1);

        for (ItemModel item : modPack.getAllItems()) {
            if (!item.getId().startsWith(ROCKWOOL_ID_PREFIX)) continue;

            boolean foundDyeRecipe = false;
            for (RecipeModel recipe : new ArrayList<RecipeModel>(item.getRecipes())) {
                if (recipe.getTools().size() != 1) continue;
                if (!recipe.getTools().contains(craftingTable)) continue;

                item.removeRecipe(recipe);

                if (foundDyeRecipe) continue;
                foundDyeRecipe = true;

                RecipeModel newRecipe = new RecipeModel(new ItemStackModel(item, 8));
                newRecipe.addTool(craftingTable);

                newRecipe.setInputAt(1, 1, recipe.getInputAt(1, 1));
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        if (row == 1 && col == 1) continue;
                        newRecipe.setInputAt(row, col, defaultRockwoolStack);
                    }
                }

                item.addRecipe(newRecipe);
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String CRAFTING_TABLE_ID = "minecraft:crafting_table";

    private static String ROCKWOOL_ID_PREFIX = "ThermalExpansion:Rockwool";

    private static String LIGHT_GRAY_ROCKWOOL = "ThermalExpansion:Rockwool:8";
}
