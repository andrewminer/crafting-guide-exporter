package com.craftingguide.exporter.extensions.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.List;
import net.minecraft.item.ItemStack;

public class AssemblyTableRecipeGatherer extends Gatherer {

    @SuppressWarnings("rawtypes")
    public void gather(ModPackModel modPack) {
        ItemModel assemblyTable = modPack.getItem(ASSEMBLY_TABLE_ID);

        for (IFlexibleRecipe<ItemStack> obj : BuildcraftRecipeRegistry.assemblyTable.getRecipes()) {
            if (!(obj instanceof FlexibleRecipe)) continue;

            FlexibleRecipe<ItemStack> rawRecipe = (FlexibleRecipe<ItemStack>) obj;

            ItemStack rawOutputStack = (ItemStack) rawRecipe.getOutput();
            ItemModel outputItem = BuildCraftExtension.findOrCreateItem(rawOutputStack, modPack);
            if (outputItem == null) continue;

            ItemStackModel outputStack = new ItemStackModel(outputItem, rawOutputStack.stackSize);
            RecipeModel model = new RecipeModel(outputStack);

            int index = 0;
            for (Object inputObj : rawRecipe.getInputs()) {
                if (inputObj instanceof List) {
                    for (Object itemStackObj : (List) inputObj) {
                        ItemStackModel stackModel = ItemStackModel.convert(((ItemStack) itemStackObj), modPack);
                        model.setInputAt(ROWS[index], COLS[index], stackModel);
                        index++;
                    }
                } else if (inputObj instanceof ItemStack) {
                    ItemStackModel stackModel = ItemStackModel.convert(((ItemStack) inputObj), modPack);
                    model.setInputAt(ROWS[index], COLS[index], stackModel);
                    index++;
                }
            }

            model.addTool(assemblyTable);
            modPack.addRecipe(model);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String ASSEMBLY_TABLE_ID = "BuildCraft|Silicon:laserTableBlock:0";

    private static int[] COLS = { 1, 0, 1, 0, 2, 2, 0, 1, 2 };

    private static int[] ROWS = { 1, 1, 0, 0, 1, 0, 2, 2, 2 };
}
