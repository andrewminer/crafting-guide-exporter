package com.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import java.io.File;

public class CraftingGuideFileManager {

    public CraftingGuideFileManager() {
        // do nothing
    }

    public CraftingGuideFileManager(String baseDir) {
        this.setBaseDir(baseDir);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public String getBaseDir() {
        return this._baseDir;
    }

    public String getDumpDir() {
        return asPath(this.getBaseDir(), "crafting-guide");
    }

    public String getItemDir(ModModel mod, ItemModel item) {
        return asPath(this.getModDir(mod), "items", slugify(item.getDisplayName()));
    }

    public String getItemIconFile(ModModel mod, ItemModel item) {
        return asPath(this.getItemDir(mod, item), "icon.png");
    }

    public String getModDir(ModModel mod) {
        return asPath(this.getDumpDir(), slugify(mod.getDisplayName()));
    }

    public String getModVersionDir(ModModel mod) {
        return asPath(this.getModDir(mod), "versions", slugify(mod.getVersion()));
    }

    public String getModVersionFile(ModModel mod) {
        return asPath(this.getModVersionDir(mod), "mod-version.cg");
    }

    public String getModFile(ModModel mod) {
        return asPath(this.getModDir(mod), "mod.cg");
    }

    public boolean ensureDir(String dir) {
        File dirFile = new File(dir);
        if (dirFile.exists()) return true;
        if (dirFile.mkdirs()) return true;
        System.err.println("Could not create directory: " + dir);
        return false;
    }

    public void setBaseDir(String newBaseDir) {
        if (newBaseDir == null) {
            newBaseDir = "";
        }

        this._baseDir = newBaseDir;
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static String asPath(String... elements) {
        StringBuffer buffer = new StringBuffer();
        boolean needsDelimiter = false;
        for (String element : elements) {
            if (needsDelimiter) buffer.append(File.separator);
            needsDelimiter = true;
            buffer.append(element);
        }
        return buffer.toString();
    }

    private static String slugify(String text) {
        if (text == null) return null;

        String result = text.toLowerCase();
        result = result.replaceAll("[^-a-zA-Z0-9._]", "_");
        result = result.replaceAll("__+", "_");
        result = result.replaceAll("^_", "");
        result = result.replaceAll("_$", "");

        return result;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String _baseDir = "";
}
