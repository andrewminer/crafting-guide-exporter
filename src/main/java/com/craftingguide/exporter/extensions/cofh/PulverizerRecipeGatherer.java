package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.PulverizerManager;
import cofh.thermalexpansion.util.crafting.PulverizerManager.RecipePulverizer;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class PulverizerRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel pulverizer = modPack.getItem(PULVERIZER_ID);

        for (RecipePulverizer rawRecipe : PulverizerManager.getRecipeList()) {
            ItemStack rawOutputStack = rawRecipe.getPrimaryOutput();
            ItemStack rawInputStack = rawRecipe.getInput();

            ItemModel output = modPack.getItem(rawOutputStack);
            ItemModel input = modPack.getItem(rawInputStack);

            RecipeModel recipe = new RecipeModel(new ItemStackModel(output, rawOutputStack.stackSize));
            recipe.setInputAt(1, 1, new ItemStackModel(input, rawInputStack.stackSize));
            recipe.addTool(pulverizer);

            modPack.addRecipe(recipe);
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static String PULVERIZER_ID = "ThermalExpansion:Machine:1";
}
