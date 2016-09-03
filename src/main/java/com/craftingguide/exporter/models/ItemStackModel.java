package com.craftingguide.exporter.models;

import java.util.Comparator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemStackModel implements Comparable<ItemStackModel> {

    public ItemStackModel(ItemModel item, int quantity) {
        this.setItem(item);
        this.setQuantity(quantity);
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
            if (!a.item.getDisplayName().equals(b.item.getDisplayName())) {
                return a.item.getDisplayName().compareTo(b.item.getDisplayName());
            }
            if (a.quantity != b.quantity) {
                return a.quantity > b.quantity ? -1 : +1;
            }
            return 0;
        }
    };

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ItemModel getItem() {
        return this.item;
    }

    private void setItem(ItemModel item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        this.item = item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = (quantity < 0) ? 0 : quantity;
    }

    // Comparable Overrides ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(ItemStackModel that) {
        if (!this.getItem().equals(that.getItem())) {
            return this.getItem().compareTo(that.getItem());
        }
        if (this.getQuantity() != that.getQuantity()) {
            return (this.getQuantity() < that.getQuantity()) ? -1 : +1;
        }
        return 0;
    }

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

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ItemModel item;

    private int quantity;
}
