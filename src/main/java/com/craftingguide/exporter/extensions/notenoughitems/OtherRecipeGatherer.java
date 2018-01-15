package com.craftingguide.exporter.extensions.notenoughitems;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiUsageRecipe;
import codechicken.nei.recipe.IUsageHandler;
import codechicken.nei.recipe.TemplateRecipeHandler;
import codechicken.nei.recipe.TemplateRecipeHandler.RecipeTransferRect;

public class OtherRecipeGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        ArrayList<IUsageHandler> machines = GuiUsageRecipe.usagehandlers;
        Iterator<ItemModel> allItems = modPack.getAllItems().iterator();
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
            Iterator<ItemModel> allItemIterator = modPack.getAllItems().iterator();
            
            String outputID = null;
            if (machine.numRecipes() == 0 && machine instanceof TemplateRecipeHandler) {
                TemplateRecipeHandler machineRecipeHandler = (TemplateRecipeHandler) machine;
                RecipeTransferRect recipeRectangle = machineRecipeHandler.transferRects.getFirst();
                Field outputIDField = null;
                try {
                    outputIDField = RecipeTransferRect.class.getDeclaredField("outputId");
                    outputIDField.setAccessible(true);
                    outputID = (String) outputIDField.get(recipeRectangle);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                machineRecipeHandler.loadCraftingRecipes(outputID);
            }

            if (!namedItems.containsKey(machine.getRecipeName())) {
                while (allItemIterator.hasNext()) {
                    ItemModel oneItem = allItemIterator.next();
                    if (oneItem.getDisplayName().equalsIgnoreCase(machine.getRecipeName()) || oneItem.getDisplayName().equalsIgnoreCase(outputID)) {
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
                RecipeModel recipeModel = new RecipeModel(ItemStackModel.convert(result, modPack));
                int index = 0;
                int[] rows = { 0, 0, 0, 1, 1, 1, 2, 2, 2 };
                int[] cols = { 0, 1, 2, 0, 1, 2, 0, 1, 2 };
                for (ItemStack input : ingredientStacks) {
                    index++;
                    if (index > 9) {
                        break;
                    }
                    recipeModel.setInputAt(rows[index - 1], cols[index - 1], ItemStackModel.convert(input, modPack));
                }
                recipeModel.addTool(item);
                for (ItemStack extra : otherStacks) {
                    if (!item.getId().equals(modPack.getItem(extra).getId())) {
                        recipeModel.addTool(modPack.getItem(extra));
                    }
                }
                modPack.addRecipe(recipeModel);
            }
        }
    }
}
