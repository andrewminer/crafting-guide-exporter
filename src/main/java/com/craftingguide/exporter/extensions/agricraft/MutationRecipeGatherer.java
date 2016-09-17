package com.craftingguide.exporter.extensions.agricraft;

import com.InfinityRaider.AgriCraft.api.v1.IMutation;
import com.InfinityRaider.AgriCraft.farming.mutation.MutationHandler;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;

public class MutationRecipeGatherer extends Gatherer {

    public void gather(ModPackModel modPack) {
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

            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private int[] COL = { 0, 2, 1, 0, 2, 1, 0, 2, 1 };

    private int[] ROW = { 1, 1, 1, 0, 0, 0, 2, 2, 2 };
}
