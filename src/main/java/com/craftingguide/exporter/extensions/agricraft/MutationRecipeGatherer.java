package com.craftingguide.exporter.extensions.agricraft;

import com.InfinityRaider.AgriCraft.api.v1.BlockWithMeta;
import com.InfinityRaider.AgriCraft.api.v1.IGrowthRequirement;
import com.InfinityRaider.AgriCraft.api.v1.IMutation;
import com.InfinityRaider.AgriCraft.farming.CropPlantHandler;
import com.InfinityRaider.AgriCraft.farming.mutation.MutationHandler;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MutationRecipeGatherer extends Gatherer {

    public void gather(ModPackModel modPack) {
        ItemModel crops = modPack.getItem(CROPS_ID);
        ItemModel dirt = modPack.getItem(DIRT_ID);

        for (IMutation rawMutation : MutationHandler.getInstance().getMutations()) {
            ItemModel outputItem = modPack.getItem(rawMutation.getResult());
            RecipeModel recipe = new RecipeModel(new ItemStackModel(outputItem, 1));

            int index = 0;
            for (ItemStack rawInputStack : rawMutation.getParents()) {
                ItemModel input = modPack.getItem(rawInputStack);
                if (input == null) continue;

                recipe.setInputAt(ROW[index], COL[index], new ItemStackModel(input, 1));
                recipe.addExtra(new ItemStackModel(input, 1));
                index++;
            }

            recipe.setInputAt(0, 1, new ItemStackModel(crops, 2));
            recipe.addExtra(new ItemStackModel(crops, 1));

            IGrowthRequirement growth = CropPlantHandler.getGrowthRequirement(outputItem.getRawItemStack());

            ItemModel soil = asItem(growth.getSoil(), modPack);
            soil = (soil != null) ? soil : dirt;
            if (soil != null) {
                recipe.setInputAt(1, 1, new ItemStackModel(soil, 1));
                recipe.addExtra(new ItemStackModel(soil, 1));
            }

            ItemModel required = asItem(growth.getRequiredBlock(), modPack);
            if (required != null) {
                recipe.setInputAt(2, 1, new ItemStackModel(required, 1));
                recipe.addExtra(new ItemStackModel(required, 1));
            }

            recipe.setIsMadeInWorld(true);
            modPack.addRecipe(recipe);
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private ItemModel asItem(BlockWithMeta rawBlock, ModPackModel modPack) {
        if (rawBlock == null) return null;

        Item rawItem = Item.getItemFromBlock(rawBlock.getBlock());
        if (rawItem == null) return null;

        ItemStack rawItemStack = new ItemStack(rawItem, 1, rawBlock.getMeta());
        return modPack.getItem(rawItemStack);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private int[] COL = { 0, 2, 1, 0, 2, 1, 0, 2, 1 };

    private String CROPS_ID = "AgriCraft:cropsItem";

    private String DIRT_ID = "minecraft:dirt";

    private int[] ROW = { 0, 0, 0, 1, 1, 1, 2, 2, 2 };
}
