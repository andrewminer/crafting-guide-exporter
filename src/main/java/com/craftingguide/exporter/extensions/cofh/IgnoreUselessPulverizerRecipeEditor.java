package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class IgnoreUselessPulverizerRecipeEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        ItemModel pulverizer = modPack.getItem(PULVERIZER_ID);

        for (ItemModel item : modPack.getAllItems()) {
            if (!item.getDisplayName().startsWith("Pulverized")) continue;

            RecipeModel preferredRecipe = null;

            if (preferredRecipe == null) {
                preferredRecipe = this.findIngotToDustRecipe(item);
            }

            if (preferredRecipe == null) {
                preferredRecipe = this.findOreToDustRecipe(item);
            }

            if (preferredRecipe != null) {
                for (RecipeModel recipe : item.getRecipes()) {
                    if (!recipe.getTools().contains(pulverizer)) continue;
                    if (recipe == preferredRecipe) continue;
                    recipe.setIgnoredDuringCrafting(true);
                }
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String PULVERIZER_ID = "ThermalExpansion:Machine:1";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private RecipeModel findIngotToDustRecipe(ItemModel item) {
        for (RecipeModel recipe : item.getRecipes()) {
            if (!recipe.getInputAt(1, 1).getItem().getDisplayName().endsWith("Ingot")) continue;
            return recipe;
        }
        return null;
    }

    private RecipeModel findOreToDustRecipe(ItemModel item) {
        for (RecipeModel recipe : item.getRecipes()) {
            if (!recipe.getInputAt(1, 1).getItem().getDisplayName().endsWith("Ore")) continue;
            return recipe;
        }
        return null;
    }
}
