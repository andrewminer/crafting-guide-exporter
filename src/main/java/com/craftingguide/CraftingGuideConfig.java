package com.craftingguide;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class CraftingGuideConfig {

    public CraftingGuideConfig(String category, CraftingGuideFileManager fileManager) {
        this.setCategory(category);
        this.setFileManager(fileManager);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void save() {
        this.getConfig().save();
    }

    // Config Value Property Methods ///////////////////////////////////////////////////////////////////////////////////

    public String getOutputDir() {
        Property p = this.getConfig().get(this.getCategory(), "outputDir", OUTPUT_DIR_DEFAULT, OUTPUT_DIR_COMMENT);
        return p.getString();
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getCategory() {
        return this.category;
    }

    private void setCategory(String category) {
        if (category == null) throw new IllegalArgumentException("category cannot be null");
        this.category = category;
    }

    private Configuration getConfig() {
        if (this.config == null) {
            this.config = new Configuration(new File(this.getFileManager().getConfigFile()));
        }
        return this.config;
    }

    public CraftingGuideFileManager getFileManager() {
        return this.fileManager;
    }

    private void setFileManager(CraftingGuideFileManager fileManager) {
        if (fileManager == null) throw new IllegalArgumentException("fileManager cannot be null");
        this.fileManager = fileManager;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String OUTPUT_DIR_COMMENT = "The directory where the Crafting Guide Exporter output will be "
        + "placed.  May either be a relative or absolute path.";

    private static String OUTPUT_DIR_DEFAULT = "dumps" + File.separator + "crafting-guide";

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String category = "";

    private Configuration config = null;

    private CraftingGuideFileManager fileManager = null;
}
