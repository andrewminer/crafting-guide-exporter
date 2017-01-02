package com.craftingguide.exporter.extensions.bigreactors;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class ReprocessorRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel cyaniteIngot = modPack.getItem(CYANITE_INGOT_ID);
        ItemModel blutoniumIngot = modPack.getItem(BLUTONIUM_INGOT_ID);
        ItemModel water = modPack.getItem(WATER_ID);

        RecipeModel recipe = new RecipeModel(new ItemStackModel(blutoniumIngot, 1));
        recipe.setInputAt(0, 1, new ItemStackModel(cyaniteIngot, 2));
        recipe.setInputAt(1, 1, new ItemStackModel(water, 1000));
        recipe.addTool(modPack.getItem(CYANITE_REPROCESSOR_ID));
        modPack.addRecipe(recipe);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String BLUTONIUM_INGOT_ID = "BigReactors:BRIngot:3";

    private static String CYANITE_INGOT_ID = "BigReactors:BRIngot:1";

    private static String CYANITE_REPROCESSOR_ID = "BigReactors:BRDevice:0";

    private static String WATER_ID = "minecraft:water";
}
