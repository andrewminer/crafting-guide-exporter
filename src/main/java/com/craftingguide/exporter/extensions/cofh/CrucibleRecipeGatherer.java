package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.CrucibleManager;
import cofh.thermalexpansion.util.crafting.CrucibleManager.RecipeCrucible;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CrucibleRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel crucible = modPack.getItem(CRUCIBLE_ID);

        for (RecipeCrucible rawRecipe : CrucibleManager.getRecipeList()) {
            ItemStack rawInputStack = rawRecipe.getInput();
            FluidStack rawOutputStack = rawRecipe.getOutput();

            RecipeModel recipe = new RecipeModel(ItemStackModel.convert(rawOutputStack, modPack));
            recipe.setInputAt(1, 1, ItemStackModel.convert(rawInputStack, modPack));
            recipe.addTool(crucible);

            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String CRUCIBLE_ID = "ThermalExpansion:Machine:4";
}
