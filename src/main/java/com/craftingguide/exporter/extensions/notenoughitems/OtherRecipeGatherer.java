package com.craftingguide.exporter.extensions.notenoughitems;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.IUsageHandler;

public class OtherRecipeGatherer extends Gatherer {
	
	@Override
	public void gather(ModPackModel modpack) {
		ArrayList<IUsageHandler> machines = GuiUsageRecipe.usagehandlers;
		Iterator<ItemModel> allItems = modpack.getAllItems().iterator();
		ArrayList<ItemModel> alreadyRegisteredMachines = new ArrayList<ItemModel>();
		while (allItems.hasNext()) {
			ItemModel item = allItems.next();
			SortedSet<RecipeModel> recipes = item.getRecipes();
			for (RecipeModel recipe : recipes) {
				if (!recipe.getTools().isEmpty()) {
					if (!alreadyRegisteredMachines.contains(recipe.getTools().first()))
					alreadyRegisteredMachines.add(recipe.getTools().first());
				}
			}
		}
		HashMap<String, ItemStack> namedItems = new HashMap<String, ItemStack>();
		for (IUsageHandler machine : machines) {
			boolean alreadyRegistered = false;
			for (ItemModel registeredMachine : alreadyRegisteredMachines) {
				if (machine.getRecipeName() == registeredMachine.getRawItemStack().getDisplayName()) {
					alreadyRegistered = true;
					break;
				}
			}
			if (alreadyRegistered) continue;
			boolean notRealItem = true;
			ItemModel item = null;
			Iterator<ItemModel> allItemIterator = modpack.getAllItems().iterator();
			if (!namedItems.containsKey(machine.getRecipeName())) {
				while (allItemIterator.hasNext()) {
					ItemModel oneItem = allItemIterator.next();
					if (oneItem.getDisplayName() == machine.getRecipeName()) {
						notRealItem = false;
						item = oneItem;
						break;
					}
				}
				if (notRealItem) {
					continue;
				}
			}
			for (int recipe = 0; recipe < machine.numRecipes(); recipe++) {
				ItemStack result = machine.getResultStack(recipe).item;
				List<PositionedStack> ingredients = machine.getIngredientStacks(recipe);
				ArrayList<ItemStack> ingredientStacks = new ArrayList<ItemStack>();
				for (PositionedStack ingredient : ingredients) {
					ingredientStacks.add(ingredient.item);
				}
				ArrayList<ItemStack> otherStacks = new ArrayList<ItemStack>();
				List<PositionedStack> others = machine.getOtherStacks(recipe);
				for (PositionedStack other : others) {
					otherStacks.add(other.item);
				}
				RecipeModel recipeModel = new RecipeModel(new ItemStackModel(modpack.getItem(result), result.stackSize));
				for (ItemStack input : ingredientStacks) {
					recipeModel.addInput(new ItemStackModel(modpack.getItem(input), input.stackSize));
				}
				for (ItemStack extra : otherStacks) {
					recipeModel.addInput(new ItemStackModel(modpack.getItem(extra), extra.stackSize));
				}
				item.addRecipe(recipeModel);
			}
		}
	}
	
}
