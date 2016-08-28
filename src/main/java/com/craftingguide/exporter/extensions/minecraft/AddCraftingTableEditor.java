package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

public class AddCraftingTableEditor implements IEditor {

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        ItemModel craftingTable = modPack.getItem("minecraft:crafting_table");

        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : item.recipes) {
                if (recipe.tools.size() > 0) continue;
                if (!recipe.isCraftingTableRequired()) continue;

                recipe.tools.add(craftingTable);
            }
        }
    }
}
