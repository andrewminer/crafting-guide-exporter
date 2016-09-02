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
            result.put(mod.getId(), modItems);

            for (ItemModel item : this.getAllItems()) {
                if (!item.isFromMod(mod.getId())) continue;
                modItems.add(item);
            }
        }

        return result;
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ItemModel getItem(String id) {
        ItemModel result = this.items.get(id);
        if (result == null) {
            List<ItemModel> oreEntries = this.oreDictionary.get(id);
            if (oreEntries != null && oreEntries.size() > 0) {
                result = oreEntries.get(0);
            }
        }
        if (result == null) {
            result = this.items.get(id + ":0");
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
        if (this.itemList == null) {
            this.itemList = new ArrayList<ItemModel>(this.items.values());
            this.itemList.sort(ItemModel.SORT_BY_DISPLAY_NAME);
        }

        return this.itemList;
    }

    public void addItem(ItemModel item) {
        ItemModel existingItem = this.getItem(item.id);
        if (existingItem != null) return;

        this.items.put(item.id, item);
        this.itemList = null;
    }

    public void removeItem(String id) {
        this.items.remove(id);
        this.itemList = null;
    }

    public void removeItem(ItemModel item) {
        this.removeItem(item.id);
    }

    public ModModel getMod(String modId) {
        return this.mods.get(modId);
    }

    public Collection<ModModel> getAllMods() {
        if (this.modList == null) {
            this.modList = new ArrayList<ModModel>(this.mods.values());
            this.modList.sort(ModModel.SORT_BY_DISPLAY_NAME);
        }
        return this.modList;
    }

    public void addMod(ModModel mod) {
        this.mods.put(mod.getId(), mod);
        this.modList = null;
    }

    public void addOreEntry(String oreId, ItemModel item) {
        List<ItemModel> oreItems = this.oreDictionary.get(oreId);
        if (oreItems == null) {
            oreItems = new ArrayList<>();
            this.oreDictionary.put(oreId, oreItems);
        }
        if (oreItems.contains(item)) return;
        oreItems.add(item);
    }

    public Map<String, List<ItemModel>> getOreDictionary() {
        return this.oreDictionary;
    }

    public void addRecipe(RecipeModel recipe) {
        ItemModel item = this.getItem(recipe.output.item.id);
        item.recipes.add(recipe);
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        return "ModPack[" + this.items.values().size() + " items]";
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private List<ItemModel> itemList = null;

    private Map<String, ItemModel> items = new HashMap<>();

    private List<ModModel> modList = null;

    private Map<String, ModModel> mods = new HashMap<>();

    private HashMap<String, List<ItemModel>> oreDictionary = new HashMap<>();
}
