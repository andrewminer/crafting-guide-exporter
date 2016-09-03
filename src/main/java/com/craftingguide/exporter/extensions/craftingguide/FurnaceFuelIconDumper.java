package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class FurnaceFuelIconDumper implements IDumper {

    public FurnaceFuelIconDumper(CraftingGuideFileManager fileManager) {
        this.setFileManager(fileManager);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideFileManager getFileManager() {
        return this.fileManager;
    }

    private void setFileManager(CraftingGuideFileManager fileManager) {
        if (fileManager == null) throw new IllegalArgumentException("fileManager cannot be null");
        this.fileManager = fileManager;
    }

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack) {
        ModModel mod = modPack.getMod("minecraft");
        ItemModel fireItem = modPack.getItem("minecraft:fire");
        ItemModel furnaceFuelItem = new ItemModel("craftingguide:furnace_fuel", "Furnace Fuel");

        String sourceImage = this.getFileManager().getItemIconFile(mod, fireItem);
        String targetDir = this.getFileManager().getItemDir(mod, furnaceFuelItem);
        String targetImage = this.getFileManager().getItemIconFile(mod, furnaceFuelItem);

        this.getFileManager().ensureDir(targetDir);

        try {
            FileUtils.copyFile(new File(sourceImage), new File(targetImage));
        } catch (IOException e) {
            System.err.println("Failed to copy Furnace Fuel icon.");
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private CraftingGuideFileManager fileManager = null;
}
