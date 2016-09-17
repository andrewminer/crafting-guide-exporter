package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;

public class WoodenItemRecipeEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        for (String baseId : ITEM_BASE_IDS) {
            this.removeExtraRecipes(baseId, modPack);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String[] ITEM_BASE_IDS = { "AgriCraft:fence", "AgriCraft:fenceGate", "AgriCraft:grate",
        "AgriCraft:channelValve", "AgriCraft:waterChannel", "AgriCraft:waterChannelFull", "AgriCraft:waterTank" };

    private static String WOODEN_ITEM_ID_PATTERN = "minecraft:planks.*|AgriCraft:waterChannel.*";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private Integer getMaterialType(RecipeModel recipe) {
        for (ItemStackModel input : recipe.getInputs()) {
            if (!input.getItem().getId().matches(WOODEN_ITEM_ID_PATTERN)) continue;
            return input.getItem().getType();
        }
        return null;
    }

    private void removeExtraRecipes(String baseItemId, ModPackModel modPack) {
        ItemModel baseItem = modPack.getItem(baseItemId + ":0");
        if (baseItem == null) return;

        for (RecipeModel recipe : new ArrayList<RecipeModel>(baseItem.getRecipes())) {
            Integer materialType = this.getMaterialType(recipe);
            if (materialType == null) continue;
            if (materialType == 0) continue;

            baseItem.removeRecipe(recipe);
        }
    }
}
