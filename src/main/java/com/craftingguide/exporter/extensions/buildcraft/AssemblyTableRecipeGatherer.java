package com.craftingguide.exporter.extensions.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IFlexibleRecipe;
import buildcraft.core.recipes.FlexibleRecipe;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.item.ItemStack;

public class AssemblyTableRecipeGatherer extends Gatherer {

    public void gather(ModPackModel modPack) {
        ItemModel assemblyTable = modPack.getItem(ASSEMBLY_TABLE_ID);

        for (IFlexibleRecipe<ItemStack> obj : BuildcraftRecipeRegistry.assemblyTable.getRecipes()) {
            if (!(obj instanceof FlexibleRecipe)) continue;

            FlexibleRecipe<ItemStack> rawRecipe = (FlexibleRecipe<ItemStack>) obj;

            ItemStack rawOutputStack = (ItemStack) rawRecipe.getOutput();
            ItemModel outputItem = BuildCraftExtension.findOrCreateItem(rawOutputStack, modPack);
            if (outputItem == null) continue;

            ItemStackModel outputStack = new ItemStackModel(outputItem, rawOutputStack.stackSize);
            RecipeModel recipe = new RecipeModel(outputStack);

            int index = 0;
            for (Object inputObj : rawRecipe.getInputs()) {
                ItemStack rawInputStack = null;
                if (inputObj instanceof List) {
                    rawInputStack = ((List<ItemStack>) inputObj).get(0);
                } else if (inputObj instanceof ItemStack) {
                    rawInputStack = (ItemStack) inputObj;
                }
                ItemStackModel stackModel = ItemStackModel.convert(rawInputStack, modPack);
                recipe.setInputAt(ROWS[index], COLS[index], stackModel);
                index++;
            }

            recipe.addTool(assemblyTable);

            if (this.shouldKeepRecipe(recipe, modPack)) {
                modPack.addRecipe(recipe);
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String ASSEMBLY_TABLE_ID = "BuildCraft|Silicon:laserTableBlock:0";

    private static int[] COLS = { 1, 0, 1, 0, 2, 2, 0, 1, 2 };

    private static int[] ROWS = { 1, 1, 0, 0, 1, 0, 2, 2, 2 };

    private static String STONE_FACADE_ID = "BuildCraft|Transport:pipeFacade:0";

    private static String STONE_ID = "minecraft:stone";

    private static String STRUCTURE_PIPE_ID = "BuildCraft|Transport:item.buildcraftPipe.pipestructurecobblestone:0";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean shouldKeepRecipe(RecipeModel recipe, ModPackModel modPack) {
        ItemModel stoneFacade = modPack.getItem(STONE_FACADE_ID);
        if (recipe.getOutput().getItem() != stoneFacade) return true;

        ItemModel stone = modPack.getItem(STONE_ID);
        ItemModel structurePipe = modPack.getItem(STRUCTURE_PIPE_ID);
        Set<ItemModel> validInputs = new HashSet<>(Arrays.asList(stone, structurePipe));
        for (ItemStackModel inputStack : recipe.getInputs()) {
            if (!validInputs.contains(inputStack.getItem())) return false;
        }
        return true;
    }
}
