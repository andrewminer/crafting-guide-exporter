package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.SawmillManager;
import cofh.thermalexpansion.util.crafting.SawmillManager.RecipeSawmill;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class SawmillRecipeGatherer extends Gatherer {

    public void gather(ModPackModel modPack) {
        ItemModel sawmill = modPack.getItem(SAWMILL_ID);

        for (RecipeSawmill rawRecipe : SawmillManager.getRecipeList()) {
            ItemStack rawOutputStack = rawRecipe.getPrimaryOutput();
            ItemStack rawInputStack = rawRecipe.getInput();

            ItemModel output = modPack.getItem(rawOutputStack);
            ItemModel input = modPack.getItem(rawInputStack);

            RecipeModel recipe = new RecipeModel(new ItemStackModel(output, rawOutputStack.stackSize));
            recipe.setInputAt(1, 1, new ItemStackModel(input, rawInputStack.stackSize));
            recipe.addTool(sawmill);

            modPack.addRecipe(recipe);
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static String SAWMILL_ID = "ThermalExpansion:Machine:2";

}
