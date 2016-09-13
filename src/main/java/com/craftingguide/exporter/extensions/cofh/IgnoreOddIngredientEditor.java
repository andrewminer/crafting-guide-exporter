package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IgnoreOddIngredientEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        Set<ItemModel> ignoredItems = new HashSet<>();
        for (String id : IGNORED_INGREDIENT_IDS) {
            ignoredItems.add(modPack.getItem(id));
        }

        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : new ArrayList<RecipeModel>(item.getRecipes())) {
                if (recipe.getTools().size() > 0) continue;

                for (ItemStackModel inputStack : recipe.getInputs()) {
                    if (!ignoredItems.contains(inputStack.getItem())) continue;

                    recipe.setIgnoredDuringCrafting(true);
                    break;
                }
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String[] IGNORED_INGREDIENT_IDS = { "ThermalFoundation:material:512" };
}
