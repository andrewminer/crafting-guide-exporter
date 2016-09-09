package com.craftingguide.exporter.extensions.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraftforge.fluids.FluidStack;

public class RefineryRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel refinery = modPack.getItem(REFINERY_ID);

        for (IFlexibleRecipe<FluidStack> rawRecipeObj : BuildcraftRecipeRegistry.refinery.getRecipes()) {
            if (!(rawRecipeObj instanceof FlexibleRecipe)) continue;

            FlexibleRecipe<FluidStack> rawRecipe = (FlexibleRecipe<FluidStack>) rawRecipeObj;
            FluidStack rawOutputStack = (FluidStack) rawRecipe.getOutput();
            ItemStackModel output = ItemStackModel.convert(rawOutputStack, modPack);
            if (output == null) continue;

            RecipeModel recipe = new RecipeModel(output);
            int index = 0;
            for (Object inputObj : rawRecipe.getInputs()) {
                if (!(inputObj instanceof FluidStack)) continue;

                FluidStack rawInputStack = (FluidStack) inputObj;
                ItemStackModel input = ItemStackModel.convert(rawInputStack, modPack);
                if (input == null) continue;

                recipe.setInputAt(ROWS[index], COLS[index], input);
                index++;
            }

            recipe.addTool(refinery);
            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private String REFINERY_ID = "BuildCraft|Factory:refineryBlock";

    private int[] ROWS = { 1, 1, 1, 0, 0, 0, 2, 2, 2 };

    private int[] COLS = { 0, 2, 1, 0, 2, 1, 0, 2, 1 };
}
