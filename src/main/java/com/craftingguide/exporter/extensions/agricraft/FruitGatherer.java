package com.craftingguide.exporter.extensions.agricraft;

import com.InfinityRaider.AgriCraft.farming.CropPlantHandler;
import com.InfinityRaider.AgriCraft.farming.cropplant.CropPlant;
import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FruitGatherer extends Gatherer {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        ItemModel defaultSoil = modPack.getItem(DEFAULT_SOIL_ID);

        for (CropPlant rawPlant : CropPlantHandler.getPlants()) {
            ItemModel seed = modPack.getItem(rawPlant.getSeed());

            ItemModel soil = null;
            if (rawPlant.getGrowthRequirement().getSoil() != null) {
                soil = modPack.getItem(rawPlant.getGrowthRequirement().getSoil().toStack());
            } else {
                soil = defaultSoil;
            }

            for (ItemStack rawFruit : rawPlant.getAllFruits()) {
                ItemModel fruit = modPack.getItem(rawFruit);

                RecipeModel recipe = new RecipeModel(new ItemStackModel(fruit, 1));
                recipe.setInputAt(0, 1, new ItemStackModel(seed, 1));
                recipe.setInputAt(1, 1, new ItemStackModel(soil, 1));
                recipe.addExtra(new ItemStackModel(seed, 1));
                modPack.addRecipe(recipe);
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    private static String DEFAULT_SOIL_ID = "minecraft:dirt";
}
