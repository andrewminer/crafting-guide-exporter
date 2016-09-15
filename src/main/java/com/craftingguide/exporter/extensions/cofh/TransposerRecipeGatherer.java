package com.craftingguide.exporter.extensions.cofh;

import cofh.thermalexpansion.util.crafting.TransposerManager;
import cofh.thermalexpansion.util.crafting.TransposerManager.RecipeTransposer;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class TransposerRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        this.gatherFillRecipes(modPack);
        this.gatherExtractRecipes(modPack);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String TRANSPOSER_ID = "ThermalExpansion:Machine:5";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void gatherExtractRecipes(ModPackModel modPack) {
        ItemModel transposer = modPack.getItem(TRANSPOSER_ID);

        for (RecipeTransposer rawRecipe : TransposerManager.getExtractionRecipeList()) {
            ItemStackModel input = ItemStackModel.convert(rawRecipe.getInput(), modPack);
            ItemStackModel output = ItemStackModel.convert(rawRecipe.getOutput(), modPack);
            ItemStackModel fluid = ItemStackModel.convert(rawRecipe.getFluid(), modPack);

            RecipeModel recipe = new RecipeModel(output);
            recipe.addExtra(fluid);
            recipe.setInputAt(1, 1, input);
            recipe.addTool(transposer);

            modPack.addRecipe(recipe);
        }
    }

    private void gatherFillRecipes(ModPackModel modPack) {
        ItemModel transposer = modPack.getItem(TRANSPOSER_ID);

        for (RecipeTransposer rawRecipe : TransposerManager.getFillRecipeList()) {
            ItemStackModel input = ItemStackModel.convert(rawRecipe.getInput(), modPack);
            ItemStackModel output = ItemStackModel.convert(rawRecipe.getOutput(), modPack);
            ItemStackModel fluid = ItemStackModel.convert(rawRecipe.getFluid(), modPack);

            RecipeModel recipe = new RecipeModel(output);
            recipe.setInputAt(1, 0, input);
            recipe.setInputAt(1, 2, fluid);
            recipe.addTool(transposer);

            modPack.addRecipe(recipe);
        }
    }
}
