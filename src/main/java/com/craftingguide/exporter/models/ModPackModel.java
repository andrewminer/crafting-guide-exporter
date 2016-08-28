package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModPackModel {

    public ModPackModel() {
        this._items = new HashMap<String, ItemModel>();
        this._itemList = null;
        this._oreDictionary = new HashMap<String, List<ItemModel>>();
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void addRecipe(RecipeModel recipe) {
        ItemModel item = this.getItem(recipe.output.item.id);
        item.recipes.add(recipe);
    }

    public void gatherItems() {
        Set<String> ids = (Set<String>) Item.itemRegistry.getKeys();
        for (String id : ids) {
            Item item = (Item) Item.itemRegistry.getObject(id);

            if (item.getHasSubtypes()) {
                ArrayList<ItemStack> subItemStacks = new ArrayList<ItemStack>();
                item.getSubItems(item, null, subItemStacks);

                for (ItemStack stack : subItemStacks) {
                    String subTypeId = id + ":" + stack.getItemDamage();
                    this._items.put(subTypeId, new ItemModel(subTypeId, stack));
                }
            } else {
                ItemStack stack = new ItemStack(item, 1, 0);
                this._items.put(id, new ItemModel(id, stack));
            }
        }

        this._itemList = null;
    }

    public void gatherOreDictionary() {
        for (String oreName : OreDictionary.getOreNames()) {
            ArrayList<ItemModel> entries = new ArrayList<ItemModel>();
            for (ItemStack itemStack : OreDictionary.getOres(oreName)) {
                ItemModel itemModel = this.getItem(itemStack);
                if (itemModel != null) {
                    entries.add(itemModel);
                }
            }
            this._oreDictionary.put(oreName, entries);
        }
    }

    public ItemModel getItem(ItemStack itemStack) {
        String id = Item.itemRegistry.getNameForObject(itemStack.getItem());
        if (itemStack.getItemDamage() > 0 && itemStack.getItemDamage() < 16) {
            id += ":" + itemStack.getItemDamage();
        }
        return this.getItem(id);
    }

    public ItemModel getItem(String id) {
        ItemModel result = this._items.get(id);
        if (result == null) {
            List<ItemModel> oreEntries = this._oreDictionary.get(id);
            if (oreEntries != null && oreEntries.size() > 0) {
                result = oreEntries.get(0);
            }
        }
        if (result == null) {
            result = this._items.get(id + ":0");
        }
        if (result == null) {
            System.err.println("Could not find an item for id: " + id);
        }
        return result;
    }

    public Map<String, List<ItemModel>> getOreDictionary() {
        return this._oreDictionary;
    }

    public Iterable<ItemModel> getAllItems() {
        if (this._itemList == null) {
            this._itemList = new ArrayList<ItemModel>(this._items.values());
            this._itemList.sort(ItemModel.SORT_BY_DISPLAY_NAME);
        }

        return this._itemList;
    }

    public void removeItem(String id) {
        this._items.remove(id);
        this._itemList = null;
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        return "ModPack[" + this._items.values().size() + "]";
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private HashMap<String, ItemModel> _items = null;

    private ArrayList<ItemModel> _itemList = null;

    private HashMap<String, List<ItemModel>> _oreDictionary = null;
}
