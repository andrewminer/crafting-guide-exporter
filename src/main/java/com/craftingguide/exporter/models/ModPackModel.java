package com.craftingguide.exporter.models;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModPackModel {

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

    public void gatherMods() {
        this._mods = new HashMap<String, ModModel>();
        this._modList = null;

        ModModel minecraft = new ModModel("minecraft", "Minecraft", Loader.MC_VERSION);
        this._mods.put(minecraft.id, minecraft);

        for (ModContainer rawMod : Loader.instance().getActiveModList()) {
            ModModel model = new ModModel(rawMod);
            this._mods.put(model.id, model);
        }
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

    public Iterable<ItemModel> getAllItems() {
        if (this._itemList == null) {
            this._itemList = new ArrayList<ItemModel>(this._items.values());
            this._itemList.sort(ItemModel.SORT_BY_DISPLAY_NAME);
        }

        return this._itemList;
    }

    public Iterable<ModModel> getAllMods() {
        if (this._modList == null) {
            this._modList = new ArrayList<ModModel>(this._mods.values());
            this._modList.sort(ModModel.SORT_BY_DISPLAY_NAME);
        }
        return this._modList;
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

    public ModModel getMod(String modId) {
        return this._mods.get(modId);
    }

    public Map<String, List<ItemModel>> getOreDictionary() {
        return this._oreDictionary;
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

    private List<ItemModel> _itemList = null;

    private Map<String, ItemModel> _items = new HashMap<>();

    private List<ModModel> _modList = null;

    private Map<String, ModModel> _mods = new HashMap<>();

    private HashMap<String, List<ItemModel>> _oreDictionary = new HashMap<>();
}
