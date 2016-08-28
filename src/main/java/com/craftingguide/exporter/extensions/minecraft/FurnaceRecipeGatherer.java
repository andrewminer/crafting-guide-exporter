package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IGatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class FurnaceRecipeGatherer implements IGatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    @SuppressWarnings("rawtypes")
    @Override
    public void gather(ModPackModel modPack) {
        ItemModel furnaceFuelItem = new ItemModel("craftingguide:furnace_fuel", "Furnace Fuel");
        ItemStackModel furnaceFuel = new ItemStackModel(furnaceFuelItem, 1);

        ItemModel furnace = modPack.getItem("minecraft:furnace");

        for (Object obj : FurnaceRecipes.smelting().getSmeltingList().entrySet()) {
            if (!(obj instanceof Map.Entry)) continue;

            Map.Entry entry = (Map.Entry) obj;
            if (!(entry.getKey() instanceof ItemStack)) continue;
            if (!(entry.getValue() instanceof ItemStack)) continue;

            ItemStackModel input = ItemStackModel.convert((ItemStack) entry.getKey(), modPack);

            RecipeModel recipe = new RecipeModel();
            recipe.output = ItemStackModel.convert((ItemStack) entry.getValue(), modPack);
            recipe.inputs = new ArrayList<ItemStackModel>(Arrays.asList(input, furnaceFuel));
            recipe.inputGrid[0][1] = input;
            recipe.inputGrid[2][1] = furnaceFuel;
            recipe.tools.add(furnace);

            modPack.addRecipe(recipe);
        }
    }
}
