package com.craftingguide.exporter.models;

import java.util.Comparator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackModel {

    public ItemStackModel(ItemModel item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    // Class Methods ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemStackModel convert(ItemStack itemStack, ModPackModel context) {
        String outputId = Item.itemRegistry.getNameForObject(itemStack.getItem());
        if (itemStack.getItemDamage() > 0 && itemStack.getItemDamage() < 16) {
            outputId += ":" + itemStack.getItemDamage();
        }

        ItemModel outputItemModel = context.getItem(outputId);
        if (outputItemModel == null) return null;

        return new ItemStackModel(outputItemModel, itemStack.stackSize);
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ItemStackModel> SORT_BY_DISPLAY_NAME = new Comparator<ItemStackModel>() {

        @Override
        public int compare(ItemStackModel a, ItemStackModel b) {
            if (!a.item.displayName.equals(b.item.displayName)) {
                return a.item.displayName.compareTo(b.item.displayName);
            }
            if (a.quantity != b.quantity) {
                return a.quantity > b.quantity ? -1 : +1;
            }
            return 0;
        }
    };

    // Public Properties ///////////////////////////////////////////////////////////////////////////////////////////////

    public ItemModel item;

    public int quantity;

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStackModel)) return false;
        ItemStackModel that = (ItemStackModel) obj;

        if (!this.item.equals(that.item)) return false;
        if (this.quantity != that.quantity) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.item.toString() + "*" + this.quantity;
    }
}
