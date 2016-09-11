package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.FurnaceManager;
import cofh.thermalexpansion.util.crafting.FurnaceManager.RecipeFurnace;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class RedstoneFurnaceRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel furnace = modPack.getItem(FURNACE_ID);

        for (RecipeFurnace rawRecipe : FurnaceManager.getRecipeList()) {
            RecipeModel recipe = new RecipeModel(ItemStackModel.convert(rawRecipe.getOutput(), modPack));
            recipe.setInputAt(1, 1, ItemStackModel.convert(rawRecipe.getInput(), modPack));
            recipe.addTool(furnace);
            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String FURNACE_ID = "ThermalExpansion:Machine:0";
}
