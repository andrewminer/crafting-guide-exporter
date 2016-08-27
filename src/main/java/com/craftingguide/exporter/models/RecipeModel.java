package com.craftingguide.exporter.models;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import java.util.List;

public class RecipeModel {

	public List<ItemStackModel> input;

	public ItemStackModel output;

	public ItemModel[][] itemGrid = {{null, null, null}, {null, null, null}, {null, null, null}};
}
