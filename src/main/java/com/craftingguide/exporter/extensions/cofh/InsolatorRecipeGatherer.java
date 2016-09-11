package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.InsolatorManager;
import cofh.thermalexpansion.util.crafting.InsolatorManager.RecipeInsolator;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class InsolatorRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel insolator = modPack.getItem(INSOLATOR_ID);

        for (RecipeInsolator rawRecipe : InsolatorManager.getRecipeList()) {
            ItemStack rawPrimaryInputStack = rawRecipe.getPrimaryInput();
            ItemStack rawSecondaryInputStack = rawRecipe.getSecondaryInput();
            ItemStack rawPrimaryOutputStack = rawRecipe.getPrimaryOutput();

            RecipeModel recipe = new RecipeModel(ItemStackModel.convert(rawPrimaryOutputStack, modPack));
            recipe.setInputAt(1, 0, ItemStackModel.convert(rawPrimaryInputStack, modPack));
            recipe.setInputAt(1, 2, ItemStackModel.convert(rawSecondaryInputStack, modPack));
            recipe.addTool(insolator);

            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String INSOLATOR_ID = "ThermalExpansion:Machine:11";
}
