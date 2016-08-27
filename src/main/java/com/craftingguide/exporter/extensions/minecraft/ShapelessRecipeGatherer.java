package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IGatherer;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;

public class ShapelessRecipeGatherer implements IGatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        CraftingManager craftingManager = CraftingManager.getInstance();
        List<IRecipe> recipes = (List<IRecipe>) craftingManager.getRecipeList();

        for (IRecipe recipe : recipes) {
            if (recipe instanceof ShapelessRecipes) {
                RecipeModel recipeModel = this._convertRecipe(modPack, (ShapelessRecipes) recipe);
                if (recipeModel != null) {
                    modPack.addRecipe(recipeModel);
                }
            }
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private RecipeModel _convertRecipe(ModPackModel modPack, ShapelessRecipes recipe) {
        RecipeModel model = new RecipeModel();
        this._addOutput(modPack, recipe, model);
        this._addInput(modPack, recipe, model);

        return model;
    }

    private void _addInput(ModPackModel modPack, ShapelessRecipes recipe, RecipeModel model) {
        model.inputs = new ArrayList<ItemStackModel>();

        int index = 0;
        for (ItemStack itemStack : (List<ItemStack>) recipe.recipeItems) {
            ItemStackModel stackModel = ItemStackModel.convert(itemStack, modPack);
            if (stackModel != null) {
                if (model.inputs.indexOf(stackModel) == -1) {
                    model.inputs.add(stackModel);
                }
                this._insertIntoGrid(stackModel, model, index);
            }
            index++;
        }
        model.inputs.sort(ItemStackModel.SORT_BY_DISPLAY_NAME);
    }

    private void _addOutput(ModPackModel modPack, ShapelessRecipes recipe, RecipeModel model) {
        ItemStack outputItemStack = recipe.getRecipeOutput();
        model.output = ItemStackModel.convert(outputItemStack, modPack);
    }

    private void _insertIntoGrid(ItemStackModel itemStack, RecipeModel recipe, int index) {
        int[] rows = { 1, 1, 0, 0, 1, 0, 2, 2, 2 };
        int[] cols = { 1, 0, 1, 0, 2, 2, 0, 1, 2 };
        recipe.inputGrid[rows[index]][cols[index]] = itemStack;
    }
}
