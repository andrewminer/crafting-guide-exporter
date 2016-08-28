package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.item.ItemStack;

public class ItemModel {

    public ItemModel(String id, ItemStack stack) {
        this(id, stack.getDisplayName());
        this.rawStack = stack;
    }

    public ItemModel(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ItemModel> SORT_BY_DISPLAY_NAME = new Comparator<ItemModel>() {

        @Override
        public int compare(ItemModel a, ItemModel b) {
            return a.displayName.compareTo(b.displayName);
        }
    };

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isFromMod(String modId) {
        int index = this.id.indexOf(':');
        if (index == -1) return false;

        String myModPrefix = this.id.substring(0, index);
        return myModPrefix.equals(modId);
    }

    // Properties //////////////////////////////////////////////////////////////////////////////////////////////////////

    public String id = "";

    public String displayName = "";

    public ArrayList<RecipeModel> recipes = new ArrayList<RecipeModel>();;

    public ItemStack rawStack = null;

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemModel)) return false;
        ItemModel that = (ItemModel) obj;

        if (!this.id.equals(that.id)) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
