package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class PrecipitatorRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel ice = modPack.getItem(ICE_ID);
        ItemModel precipitator = modPack.getItem(PRECIPITATOR_ID);
        ItemModel snow = modPack.getItem(SNOW_ID);
        ItemModel snowball = modPack.getItem(SNOWBALL_ID);
        ItemModel water = modPack.getItem(WATER_ID);

        RecipeModel recipe = new RecipeModel(new ItemStackModel(snowball, 4));
        recipe.setInputAt(1, 1, new ItemStackModel(water, 250));
        recipe.addTool(precipitator);
        modPack.addRecipe(recipe);

        recipe = new RecipeModel(new ItemStackModel(snow, 1));
        recipe.setInputAt(1, 1, new ItemStackModel(water, 500));
        recipe.addTool(precipitator);
        modPack.addRecipe(recipe);

        recipe = new RecipeModel(new ItemStackModel(ice, 1));
        recipe.setInputAt(1, 1, new ItemStackModel(water, 1000));
        recipe.addTool(precipitator);
        modPack.addRecipe(recipe);

        /*
         * The following code seems like it ought to read the recipes for the precipitator, but for whatever reason, the
         * returned list is empty. Instead, the few recipes for this machine have been hard-coded into the file for the
         * time-being. Issue #16 tracks this problem.
         */

        // PrecipitatorManager.loadRecipes();
        // for (RecipePrecipitator rawRecipe : PrecipitatorManager.getRecipeList()) {
        // RecipeModel recipe = null;
        //
        // for (ItemStack rawOutput : rawRecipe.getOutputs()) {
        // if (recipe == null) {
        // recipe = new RecipeModel(ItemStackModel.convert(rawOutput, modPack));
        // } else {
        // recipe.addExtra(ItemStackModel.convert(rawOutput, modPack));
        // }
        // }
        //
        // if (recipe == null) continue;
        //
        // int index = 0;
        // for (FluidStack rawInput : rawRecipe.getInputs()) {
        // recipe.setInputAt(ROWS[index], COLS[index], ItemStackModel.convert(rawInput, modPack));
        // index++;
        // }
        //
        // recipe.addTool(precipitator);
        // modPack.addRecipe(recipe);
        // }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int[] COLS = { 1, 0, 2, 1, 0, 2, 1, 0, 2 };

    private static String ICE_ID = "minecraft:ice";

    private static String PRECIPITATOR_ID = "ThermalExpansion:Machine:6";

    private static int[] ROWS = { 1, 1, 1, 0, 0, 0, 2, 2, 2 };

    private static String SNOW_ID = "minecraft:snow";

    private static String SNOWBALL_ID = "minecraft:snowball";

    private static String WATER_ID = "minecraft:water";
}
