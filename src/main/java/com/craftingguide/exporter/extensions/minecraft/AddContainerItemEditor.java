package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class AddContainerItemEditor implements IEditor {

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            ItemStack containerStack = item.getRawStack().getItem().getContainerItem(item.getRawStack());
            if (containerStack == null) continue;

            ItemStackModel container = ItemStackModel.convert(containerStack, modPack);
            this.addToRecipes(item, container, modPack);
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void addToRecipes(ItemModel containedItem, ItemStackModel container, ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : item.getRecipes()) {
                int quantity = recipe.getQuantityRequired(containedItem.getId());
                if (quantity == 0) continue;

                recipe.addExtra(new ItemStackModel(container.getItem(), container.getQuantity() * quantity));
            }
        }
    }
}
