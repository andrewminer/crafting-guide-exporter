package com.craftingguide.exporter.models;

import net.minecraft.item.Item;

public class ItemModel {

	public String name = "";

	public String displayName = "";

	public ItemModel(String name, Item item) {
		this.name = name;
		this.displayName = item.getUnlocalizedName();
	}
}
