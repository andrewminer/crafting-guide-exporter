package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class AddContainerItemEditor extends Editor {

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            ItemStack containerStack = item.getRawItemStack().getItem().getContainerItem(item.getRawItemStack());
            if (containerStack == null) continue;

            ItemStackModel container = ItemStackModel.convert(containerStack, modPack);
            this.addToRecipes(item, container, modPack);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String CRAFTING_TABLE_ID = "minecraft:crafting_table";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void addToRecipes(ItemModel containedItem, ItemStackModel container, ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : item.getRecipes()) {
                int quantity = recipe.getQuantityRequired(containedItem.getId());
                if (quantity == 0) continue;

                if (!this.isCraftedRecipe(recipe, modPack)) continue;

                recipe.addExtra(new ItemStackModel(container.getItem(), container.getQuantity() * quantity));
            }
        }
    }

    private boolean isCraftedRecipe(RecipeModel recipe, ModPackModel modPack) {
        if (recipe.getTools().size() == 0) return true;
        if (recipe.getTools().size() > 1) return false;

        ItemModel craftingTable = modPack.getItem(CRAFTING_TABLE_ID);
        if (recipe.getTools().contains(craftingTable)) return true;
        return false;
    }
}
