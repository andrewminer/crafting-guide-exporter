package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IGatherer;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ShapelessRecipeGatherer implements IGatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        CraftingManager craftingManager = CraftingManager.getInstance();
        List<IRecipe> recipes = (List<IRecipe>) craftingManager.getRecipeList();

        for (IRecipe rawRecipe : recipes) {
            RecipeAdapter recipe = new RecipeAdapter(rawRecipe);

            if (recipe.isShapeless()) {
                RecipeModel recipeModel = this._convertRecipe(modPack, recipe);
                if (recipeModel == null) continue;

                modPack.addRecipe(recipeModel);
            }
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private RecipeModel _convertRecipe(ModPackModel modPack, RecipeAdapter recipe) {
        ItemStack outputItemStack = recipe.getOutput();
        if (outputItemStack == null) return null;

        RecipeModel model = new RecipeModel(ItemStackModel.convert(outputItemStack, modPack));

        int[] rows = { 1, 1, 0, 0, 1, 0, 2, 2, 2 };
        int[] cols = { 1, 0, 1, 0, 2, 2, 0, 1, 2 };
        int index = 0;

        for (ItemStack itemStack : recipe.getInputs()) {
            ItemStackModel stackModel = ItemStackModel.convert(itemStack, modPack);
            model.setInputAt(rows[index], cols[index], stackModel);
            index++;
        }

        return model;
    }
}
