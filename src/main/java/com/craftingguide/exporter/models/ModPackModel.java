package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModPackModel {

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
        String baseId = Item.itemRegistry.getNameForObject(itemStack.getItem());
        String fullId = baseId + ":" + itemStack.getItemDamage();

        ItemModel item = this.getItem(fullId);
        if (item != null) return item;

        item = this.getItem(baseId);
        return item;
    }

    public Iterable<ItemModel> getAllItems() {
        if (this.itemList == null) {
            this.itemList = new ArrayList<ItemModel>(this.items.values());
            this.itemList.sort(ItemModel.SORT_BY_DISPLAY_NAME);
        }

        return this.itemList;
    }

    public void addItem(ItemModel item) {
        ItemModel existingItem = this.getItem(item.getId());

        if (item.isFluid()) {
            if (existingItem != null && !existingItem.isFluid()) {
                existingItem.setRawFluidStack(item.getRawFluidStack());
            }
        }

        if (existingItem != null) return;

        boolean foundMod = false;
        for (ModModel mod : this.getAllMods()) {
            if (!mod.containsItemId(item.getId())) continue;

            mod.addItem(item);
            foundMod = true;
            break;
        }

        if (foundMod) {
            this.items.put(item.getId(), item);
            this.itemList = null;
        } else {
            LOGGER.warn("Could not find mod for item: " + item);
        }
    }

    public void removeItem(String id) {
        ItemModel item = this.getItem(id);
        if (item == null) return;

        this.removeItem(item);
    }

    public void removeItem(ItemModel item) {
        if (item == null) return;

        this.items.remove(item.getId());
        this.itemList = null;

        for (ModModel mod : this.getAllMods()) {
            mod.removeItem(item);
        }
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
        ItemModel item = recipe.getOutput().getItem();
        if (item.getRecipes().contains(recipe) || recipe.getInputs().isEmpty()) {
            return;
        }
        item.addRecipe(recipe);
    }

    public void removeRecipe(RecipeModel recipe) {
        ItemModel item = this.getItem(recipe.getOutput().getItem().getId());
        item.removeRecipe(recipe);
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public String toString() {
        return "ModPack[" + this.items.values().size() + " items]";
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private List<ItemModel> itemList = null;

    private Map<String, ItemModel> items = new HashMap<>();

    private List<ModModel> modList = null;

    private Map<String, ModModel> mods = new HashMap<>();

    private HashMap<String, List<ItemModel>> oreDictionary = new HashMap<>();
}
