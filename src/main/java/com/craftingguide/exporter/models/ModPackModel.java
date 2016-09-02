package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModPackModel {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public Map<String, List<ItemModel>> getItemsByMod() {
        Map<String, List<ItemModel>> result = new HashMap<>();

        for (ModModel mod : this.getAllMods()) {
            List<ItemModel> modItems = new ArrayList<ItemModel>();
            result.put(mod.id, modItems);

            for (ItemModel item : this.getAllItems()) {
                if (!item.isFromMod(mod.id)) continue;
                modItems.add(item);
            }
        }

        return result;
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

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
        return result;
    }

    public ItemModel getItem(ItemStack itemStack) {
        String id = Item.itemRegistry.getNameForObject(itemStack.getItem());
        if (itemStack.getItemDamage() > 0 && itemStack.getItemDamage() < 16) {
            id += ":" + itemStack.getItemDamage();
        }
        return this.getItem(id);
    }

    public Iterable<ItemModel> getAllItems() {
        if (this._itemList == null) {
            this._itemList = new ArrayList<ItemModel>(this._items.values());
            this._itemList.sort(ItemModel.SORT_BY_DISPLAY_NAME);
        }

        return this._itemList;
    }

    public void addItem(ItemModel item) {
        ItemModel existingItem = this.getItem(item.id);
        if (existingItem != null) return;

        this._items.put(item.id, item);
        this._itemList = null;
    }

    public void removeItem(String id) {
        this._items.remove(id);
        this._itemList = null;
    }

    public void removeItem(ItemModel item) {
        this.removeItem(item.id);
    }

    public ModModel getMod(String modId) {
        return this._mods.get(modId);
    }

    public Collection<ModModel> getAllMods() {
        if (this._modList == null) {
            this._modList = new ArrayList<ModModel>(this._mods.values());
            this._modList.sort(ModModel.SORT_BY_DISPLAY_NAME);
        }
        return this._modList;
    }

    public void addMod(ModModel mod) {
        this._mods.put(mod.id, mod);
        this._modList = null;
    }

    public void addOreEntry(String oreId, ItemModel item) {
        List<ItemModel> oreItems = this._oreDictionary.get(oreId);
        if (oreItems == null) {
            oreItems = new ArrayList<>();
            this._oreDictionary.put(oreId, oreItems);
        }
        if (oreItems.contains(item)) return;
        oreItems.add(item);
    }

    public Map<String, List<ItemModel>> getOreDictionary() {
        return this._oreDictionary;
    }

    public void addRecipe(RecipeModel recipe) {
        ItemModel item = this.getItem(recipe.output.item.id);
        item.recipes.add(recipe);
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        return "ModPack[" + this._items.values().size() + " items]";
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private List<ItemModel> _itemList = null;

    private Map<String, ItemModel> _items = new HashMap<>();

    private List<ModModel> _modList = null;

    private Map<String, ModModel> _mods = new HashMap<>();

    private HashMap<String, List<ItemModel>> _oreDictionary = new HashMap<>();
}
