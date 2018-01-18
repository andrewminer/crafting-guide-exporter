package com.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import java.io.File;

public class CraftingGuideFileManager {

    // Public Class Methods ////////////////////////////////////////////////////////////////////////////////////////////

    public static String asPath(String... elements) {
        StringBuffer buffer = new StringBuffer();
        boolean needsDelimiter = false;
        for (String element : elements) {
            if (needsDelimiter) buffer.append(File.separator);
            needsDelimiter = true;
            buffer.append(element);
        }
        return buffer.toString();
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public String getConfigDir() {
        return "config";
    }

    public String getConfigFile() {
        return asPath(this.getConfigDir(), "crafting-guide.cfg");
    }

    public String getDumpDir() {
        return this.dumpDir;
    }

    public void setDumpDir(String dumpDir) {
        this.dumpDir = (dumpDir == null) ? "dumps" : dumpDir;
    }

    public String getItemDir(ModModel mod, ItemModel item) {
        return asPath(this.getModDir(mod), "items", this.slugifier.slugify(item.getItemId()));
    }

    public String getItemIconFile(ModModel mod, ItemModel item) {
        return asPath(this.getItemDir(mod, item), "icon.png");
    }

    public String getModDir(ModModel mod) {
        return asPath(this.getDumpDir(), this.slugifier.slugify(mod.getDisplayName()));
    }

    public String getModIconFile(ModModel mod) {
        return asPath(this.getModDir(mod), "icon.png");
    }

    public String getModVersionDir(ModModel mod) {
        return asPath(this.getModDir(mod), "versions", this.slugifier.slugify(mod.getVersion()));
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

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String dumpDir = "";

    private Slugifier slugifier = new Slugifier();
}
