package com.craftingguide.exporter.models;

import java.util.Comparator;

import net.minecraft.item.ItemStack;

public class ItemModel {

	public ItemModel(String id, ItemStack stack) {
		this.id          = id;
		this.displayName = stack.getDisplayName();
		this.stack       = stack;
	}
	
	// Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static Comparator<ItemModel> SORT_BY_DISPLAY_NAME = new Comparator<ItemModel>() {

		@Override
		public int compare(ItemModel a, ItemModel b) {
			return a.displayName.compareTo(b.displayName);
		}
	};
	
	// Properties //////////////////////////////////////////////////////////////////////////////////////////////////////

	public String id = "";

	public String displayName = "";
	
	public ItemStack stack = null;
}
