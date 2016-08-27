package com.craftingguide.exporter.models;

import com.craftingguide.exporter.models.ItemModel;

public class ItemStackModel {

	public ItemModel item;

	public int quantity;

	public ItemStackModel(ItemModel item, int quantity) {
		this.item     = item;
		this.quantity = quantity;
	}
}
