package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

public class ShapedRecipeGatherer extends Gatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        CraftingManager craftingManager = CraftingManager.getInstance();
        List<IRecipe> recipes = (List<IRecipe>) craftingManager.getRecipeList();

        for (IRecipe rawRecipe : recipes) {
            RecipeAdapter recipe = new RecipeAdapter(rawRecipe);

            if (recipe.isShaped()) {
                RecipeModel recipeModel = this.convertRecipe(modPack, recipe);
                if (recipeModel == null) continue;

                modPack.addRecipe(recipeModel);
            }
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private RecipeModel convertRecipe(ModPackModel modPack, RecipeAdapter recipe) {
        ItemStackModel output = ItemStackModel.convert(recipe.getOutput(), modPack);
        if (output == null) return null;

        RecipeModel model = new RecipeModel(output);

        int index = 0;
        for (ItemStack itemStack : recipe.getInputs()) {
            if (itemStack != null && itemStack.getItem() != null) {
                ItemStackModel itemStackModel = ItemStackModel.convert(itemStack, modPack);
                if (itemStackModel != null) {
                    itemStackModel.setQuantity(1);

                    int row = (int) (index / recipe.getWidth());
                    int col = index - (row * recipe.getWidth());
                    model.setInputAt(row, col, itemStackModel);
                }
            }
            index++;
        }

        return model;
    }
}
