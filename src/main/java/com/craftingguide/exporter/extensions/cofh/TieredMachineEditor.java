package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;

public class TieredMachineEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        this.modPack = modPack;

        for (ItemModel item : modPack.getAllItems()) {
            if (!item.getId().startsWith(MACHINE_BASE_ID)) continue;
            if (!item.getDisplayName().endsWith("(Resonant)")) continue;

            String baseId = item.getId();
            this.correctBasicItem(item);

            RecipeModel baseRecipe = item.getRecipes().first();

            item = this.addVariant(baseId, item, "Hardened", ELECTRUM_GEAR_ID, INVAR_INGOT_ID);
            this.addMachineFrameRecipe(item, baseRecipe, modPack.getItem(HARDENED_FRAME_ID));
            modPack.addItem(item);

            item = this.addVariant(baseId, item, "Reinforced", SIGNALUM_GEAR_ID, HARDENED_GLASS_ID);
            this.addMachineFrameRecipe(item, baseRecipe, modPack.getItem(REINFORCED_FRAME_ID));
            modPack.addItem(item);

            item = this.addVariant(baseId, item, "Resonant", ENDERIUM_GEAR_ID, SILVER_INGOT_ID);
            this.addMachineFrameRecipe(item, baseRecipe, modPack.getItem(RESONANT_FRAME_ID));
            modPack.addItem(item);
        }

        this.modPack = null;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String ELECTRUM_GEAR_ID = "ThermalFoundation:material:135";

    private static String ENDERIUM_GEAR_ID = "ThermalFoundation:material:140";

    private static String HARDENED_FRAME_ID = "ThermalExpansion:Frame:1";

    private static String HARDENED_GLASS_ID = "ThermalExpansion:Glass:0";

    private static String INVAR_INGOT_ID = "ThermalFoundation:material:72";

    private static String MACHINE_BASE_ID = "ThermalExpansion:Machine";

    private static String MACHINE_FRAME_ID = "ThermalExpansion:Frame:0";

    private static String REINFORCED_FRAME_ID = "ThermalExpansion:Frame:2";

    private static String RESONANT_FRAME_ID = "ThermalExpansion:Frame:0";

    private static String SIGNALUM_GEAR_ID = "ThermalFoundation:material:138";

    private static String SILVER_INGOT_ID = "ThermalFoundation:material:66";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void addMachineFrameRecipe(ItemModel item, RecipeModel baseRecipe, ItemModel frame) {
        RecipeModel recipe = new RecipeModel(new ItemStackModel(item, 1));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (row == 1 && col == 1) {
                    recipe.setInputAt(row, col, new ItemStackModel(frame, 1));
                } else {
                    ItemStackModel input = baseRecipe.getInputAt(row, col);
                    if (input != null) {
                        recipe.setInputAt(row, col, input);
                    }
                }
            }
        }

        item.addRecipe(recipe);
    }

    private ItemModel addVariant(String id, ItemModel priorItem, String name, String gearId, String ingotId) {
        ItemModel item = new ItemModel(id + ":" + name, priorItem.getRawItemStack());
        item.setDisplayName(item.getDisplayName().replaceAll("\\(.*\\)", "(" + name + ")"));
        modPack.addItem(item);

        ItemStackModel gear = new ItemStackModel(modPack.getItem(gearId), 1);
        ItemStackModel ingot = new ItemStackModel(modPack.getItem(ingotId), 1);

        RecipeModel recipe = new RecipeModel(new ItemStackModel(item, 1));
        recipe.setInputAt(1, 1, new ItemStackModel(priorItem, 1));
        recipe.setInputAt(0, 1, gear);
        recipe.setInputAt(0, 0, ingot);
        recipe.setInputAt(0, 2, ingot);
        recipe.setInputAt(2, 0, ingot);
        recipe.setInputAt(2, 2, ingot);
        item.addRecipe(recipe);

        return item;
    }

    private void correctBasicItem(ItemModel item) {
        item.setDisplayName(item.getDisplayName().replaceAll("Resonant", "Basic"));

        ItemStackModel machineFrame = new ItemStackModel(modPack.getItem(MACHINE_FRAME_ID), 1);
        for (RecipeModel recipe : new ArrayList<RecipeModel>(item.getRecipes())) {
            if (recipe.getInputs().contains(machineFrame)) continue;
            modPack.removeRecipe(recipe);
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ModPackModel modPack = null;
}
