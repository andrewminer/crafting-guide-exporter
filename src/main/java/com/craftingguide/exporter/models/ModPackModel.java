package com.craftingguide.exporter.models;

import com.craftingguide.exporter.models.ItemModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ModPackModel {

	public ModPackModel() {
		this._items = new ArrayList<ItemModel>();
	}

	// Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

	public void gatherItems() {
		Set<String> ids = (Set<String>) Item.itemRegistry.getKeys();
		for (String id : ids) {
			Item item = (Item) Item.itemRegistry.getObject(id);
			
			if (item.getHasSubtypes()) {
				ArrayList<ItemStack> subItemStacks = new ArrayList<ItemStack>();
				item.getSubItems(item, null, subItemStacks);
				
				for (ItemStack stack : subItemStacks) {
					String subTypeId = id + ":" + stack.getItemDamage();
					this._items.add(new ItemModel(subTypeId, stack));
				}
			} else {
				ItemStack stack = new ItemStack(item, 1, 0);
				this._items.add(new ItemModel(id, stack));
			}
		}
		
		this._items.sort(ItemModel.SORT_BY_DISPLAY_NAME);
	}

	public List<ItemModel> getAllItems() {
		return new ArrayList<ItemModel>(this._items);
	}
	
	public void removeItem(String id) {
		int index = 0;
		while (index < this._items.size()) {
			ItemModel item = this._items.get(index);
			if (item.id.equals(id)) {
				this._items.remove(index);
			} else {
				index++;
			}
		}
	}
	
	// Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

	private List<ItemModel> _items = null;
}
