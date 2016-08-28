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
            ItemStack containerStack = item.rawStack.getItem().getContainerItem(item.rawStack);
            if (containerStack == null) continue;

            ItemStackModel container = ItemStackModel.convert(containerStack, modPack);
            this._addToRecipes(item, container, modPack);
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void _addToRecipes(ItemModel containedItem, ItemStackModel container, ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            for (RecipeModel recipe : item.recipes) {
                int quantity = recipe.getQuantityRequired(containedItem.id);
                if (quantity == 0) continue;

                recipe.extras.add(new ItemStackModel(container.item, container.quantity * quantity));
            }
        }
    }
}
