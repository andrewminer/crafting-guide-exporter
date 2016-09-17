package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;

public class WoodenItemEditor extends Editor {

    // Editor Override /////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        ItemModel item = modPack.getItem(FULL_WATER_CHANNEL_ID);
        item.setDisplayName("Full Irrigation Channel");

        item = modPack.getItem(WATER_CHANNEL_ID);
        item.setDisplayName("Irrigation Channel");
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String FULL_WATER_CHANNEL_ID = "AgriCraft:waterChannelFull:0";

    private static String WATER_CHANNEL_ID = "AgriCraft:waterChannel:0";
}
