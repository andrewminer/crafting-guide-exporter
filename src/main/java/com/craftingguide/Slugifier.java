package com.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;

public class Slugifier {

    public Slugifier() {}

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public String slugify(ItemModel item) {
        String result = this.slugify(item.getDisplayName());
        if (item.getMod() == null) {
            System.out.println("Item has no mod: " + item.getDisplayName());
        }
        if ((this.contextMod != null) && (item.getMod() != null)) {
            if (item.getMod() != this.contextMod) {
                result = this.slugify(item.getMod().getDisplayName()) + "__" + result;
            }
        }
        return result;
    }

    public String slugify(String text) {
        if (text == null) return null;

        String result = text.toLowerCase();
        result = result.replaceAll("[^a-zA-Z0-9._]", "_");
        result = result.replaceAll("__+", "_");
        result = result.replaceAll("^_", "");
        result = result.replaceAll("_$", "");
        return result;
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ModModel getContextMod() {
        return this.contextMod;
    }

    public void setContextMod(ModModel mod) {
        this.contextMod = mod;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ModModel contextMod = null;
}
