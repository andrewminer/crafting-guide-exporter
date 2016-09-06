package com.craftingguide.exporter.extensions.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class AssemblyTableRecipeGatherer extends Gatherer {

    public void gather(ModPackModel modPack) {
        for (IFlexibleRecipe<ItemStack> obj : BuildcraftRecipeRegistry.assemblyTable.getRecipes()) {
            if (!(obj instanceof FlexibleRecipe)) continue;

            FlexibleRecipe<ItemStack> rawRecipe = (FlexibleRecipe<ItemStack>) obj;

            ItemStackModel outputStack = ItemStackModel.convert(rawRecipe.output, modPack);
            if (outputStack == null) continue;

            RecipeModel model = new RecipeModel(outputStack);

            int[] rows = { 1, 1, 0, 0, 1, 0, 2, 2, 2 };
            int[] cols = { 1, 0, 1, 0, 2, 2, 0, 1, 2 };
            int index = 0;

            for (ItemStack itemStack : rawRecipe.inputItems) {
                ItemStackModel stackModel = ItemStackModel.convert(itemStack, modPack);
                model.setInputAt(rows[index], cols[index], stackModel);
                index++;
            }

            // modPack.addRecipe(model);
        }
    }
}
