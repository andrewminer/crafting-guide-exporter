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

public class ShapedRecipeGatherer implements IGatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        CraftingManager craftingManager = CraftingManager.getInstance();
        List<IRecipe> recipes = (List<IRecipe>) craftingManager.getRecipeList();

        for (IRecipe rawRecipe : recipes) {
            RecipeAdapter recipe = new RecipeAdapter(rawRecipe);

            if (recipe.isShaped()) {
                RecipeModel recipeModel = this._convertRecipe(modPack, recipe);

                if (recipeModel == null) continue;
                if (recipeModel.output == null) continue;

                modPack.addRecipe(recipeModel);
            }
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void _addInput(ModPackModel modPack, RecipeAdapter recipe, RecipeModel model) {
        model.inputs = new ArrayList<ItemStackModel>();

        int index = 0;
        for (ItemStack itemStack : recipe.getInputs()) {
            if (itemStack != null) {
                ItemStackModel itemStackModel = ItemStackModel.convert(itemStack, modPack);
                if (itemStackModel != null) {
                    itemStackModel.quantity = 1;

                    if (model.inputs.indexOf(itemStackModel) == -1) {
                        model.inputs.add(itemStackModel);
                    }
                    this._insertIntoGrid(itemStackModel, recipe, model, index);
                }
            }
            index++;
        }
    }

    private void _addOutput(ModPackModel modPack, RecipeAdapter recipe, RecipeModel model) {
        ItemStack outputItemStack = recipe.getOutput();
        model.output = ItemStackModel.convert(outputItemStack, modPack);
    }

    private RecipeModel _convertRecipe(ModPackModel modPack, RecipeAdapter recipe) {
        RecipeModel model = new RecipeModel();
        this._addOutput(modPack, recipe, model);
        this._addInput(modPack, recipe, model);

        return model;
    }

    private void _insertIntoGrid(ItemStackModel stack, RecipeAdapter recipe, RecipeModel model, int index) {
        int row = (int) (index / recipe.getWidth());
        int col = index - (row * recipe.getWidth());
        model.inputGrid[row][col] = stack;
    }
}
