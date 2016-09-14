package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;

public class FlorbEditor extends Editor {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        this.processFlorb(modPack.getItem(FLORB_ID));
        this.processFlorb(modPack.getItem(MAGMATIC_FLORB_ID));
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String FLORB_ID = "ThermalExpansion:florb:0";

    private static String MAGMATIC_FLORB_ID = "ThermalExpansion:florb:1";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void processFlorb(ItemModel florb) {
        florb.setDisplayName(florb.getDisplayName().replaceAll("§r", ""));

        for (RecipeModel recipe : new ArrayList<RecipeModel>(florb.getRecipes())) {
            if (recipe.getTools().size() == 0) continue;
            florb.removeRecipe(recipe);
        }
    }
}
