package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class ExtruderRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel extruder = modPack.getItem(EXTRUDER_ID);

        ItemStackModel cobblestone = new ItemStackModel(modPack.getItem(COBBLESTONE_ID), 1);
        ItemStackModel lava = new ItemStackModel(modPack.getItem(LAVA_ID), 1000);
        ItemStackModel obsidian = new ItemStackModel(modPack.getItem(OBSIDIAN_ID), 1);
        ItemStackModel stone = new ItemStackModel(modPack.getItem(STONE_ID), 1);
        ItemStackModel water = new ItemStackModel(modPack.getItem(WATER_ID), 1000);

        RecipeModel recipe = new RecipeModel(cobblestone);
        recipe.addExtra(lava);
        recipe.addExtra(water);
        recipe.setInputAt(1, 0, water);
        recipe.setInputAt(1, 2, lava);
        recipe.addTool(extruder);
        modPack.addRecipe(recipe);

        recipe = new RecipeModel(stone);
        recipe.addExtra(lava);
        recipe.setInputAt(1, 0, water);
        recipe.setInputAt(1, 2, lava);
        recipe.addTool(extruder);
        modPack.addRecipe(recipe);

        recipe = new RecipeModel(obsidian);
        recipe.setInputAt(1, 0, water);
        recipe.setInputAt(1, 2, lava);
        recipe.addTool(extruder);
        modPack.addRecipe(recipe);

        /*
         * The following code seems like it ought to read the recipes for the extruder, but, for whatever reason, the
         * returned list is empty. Instead, the few recipes for this machine have been hard-coded into the file for the
         * time-being. Issue #16 tracks this problem.
         */

        // ItemModel extruder = modPack.getItem(EXTRUDER_ID);
        //
        // for (RecipeExtruder rawRecipe : ExtruderManager.getRecipeList()) {
        // RecipeModel recipe = null;
        // for (ItemStack rawOutputStack : rawRecipe.getOutputs()) {
        // if (recipe == null) {
        // recipe = new RecipeModel(ItemStackModel.convert(rawOutputStack, modPack));
        // } else {
        // recipe.addExtra(ItemStackModel.convert(rawOutputStack, modPack));
        // }
        // }
        //
        // recipe.setInputAt(1, 0, ItemStackModel.convert(rawRecipe.getColdInput(), modPack));
        // recipe.setInputAt(1, 2, ItemStackModel.convert(rawRecipe.getHotInput(), modPack));
        // recipe.addTool(extruder);
        //
        // modPack.addRecipe(recipe);
        // }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String COBBLESTONE_ID = "minecraft:cobblestone";

    private static String EXTRUDER_ID = "ThermalExpansion:Machine:7";

    private static String LAVA_ID = "minecraft:lava";

    private static String OBSIDIAN_ID = "minecraft:obsidian";

    private static String STONE_ID = "minecraft:stone";

    private static String WATER_ID = "minecraft:water";

}
