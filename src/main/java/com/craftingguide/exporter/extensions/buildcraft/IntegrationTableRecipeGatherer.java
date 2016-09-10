package com.craftingguide.exporter.extensions.buildcraft;

import buildcraft.api.recipes.BuildcraftRecipeRegistry;
import buildcraft.api.recipes.IIntegrationRecipe;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.Arrays;
import net.minecraft.item.ItemStack;

public class IntegrationTableRecipeGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel integrationTable = modPack.getItem(INTEGRATION_TABLE_ID);

        for (IIntegrationRecipe rawRecipe : BuildcraftRecipeRegistry.integrationTable.getRecipes()) {
            for (ItemStack rawInput : rawRecipe.getExampleInput()) {
                for (ItemStack rawExpansion : rawRecipe.getExampleExpansions().get(0)) {

                    ItemModel x = BuildCraftExtension.findOrCreateItem(rawInput, modPack);
                    if (x.getDisplayName().contains("Robot")) {
                        System.out.println("found robot");
                    }

                    if (!rawRecipe.isValidExpansion(rawInput, rawExpansion)) continue;

                    ItemStack rawOutput = rawRecipe.craft(rawInput, Arrays.asList(rawExpansion), true);
                    if (rawOutput == null) continue;

                    ItemModel output = BuildCraftExtension.findOrCreateItem(rawOutput, modPack);
                    ItemModel input = BuildCraftExtension.findOrCreateItem(rawInput, modPack);
                    ItemModel expansion = BuildCraftExtension.findOrCreateItem(rawExpansion, modPack);

                    RecipeModel recipe = new RecipeModel(new ItemStackModel(output, 1));
                    recipe.setInputAt(1, 0, new ItemStackModel(input, 1));
                    recipe.setInputAt(1, 2, new ItemStackModel(expansion, 1));
                    recipe.addTool(integrationTable);

                    modPack.addRecipe(recipe);
                }
            }
        }
    }

    // Private Class Variables /////////////////////////////////////////////////////////////////////////////////////////

    private static String INTEGRATION_TABLE_ID = "BuildCraft|Silicon:laserTableBlock:2";
}
