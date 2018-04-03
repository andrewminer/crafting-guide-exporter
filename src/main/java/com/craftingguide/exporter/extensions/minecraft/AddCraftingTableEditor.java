package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class AddCraftingTableEditor extends Editor {

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        ItemModel craftingTable = modPack.getItem("minecraft:crafting_table");

        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : item.getRecipes()) {
                if (!recipe.getTools().isEmpty()) continue;
                if (!recipe.isCraftingTableRequired()) continue;

                recipe.addTool(craftingTable);
            }
        }
    }
}
