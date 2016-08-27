package com.craftingguide.exporter.models;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.item.Item;

public class ModPackModel {

	private List<ItemModel> _items = null;

	public ModPackModel() {
		this._items = new ArrayList<ItemModel>();
	}

	// Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

	public void gatherItems() {
		for (String name : (Set<String>) Item.itemRegistry.getKeys()) {
			Item minecraftItem = (Item) Item.itemRegistry.getObject(name);
			this._items.add(new ItemModel(name, minecraftItem));
		}
	}

	public List<ItemModel> getAllItems() {
		return this._items;
	}

	public void addRecipe(ItemModel outputItem, RecipeModel recipe) {
	}
}
