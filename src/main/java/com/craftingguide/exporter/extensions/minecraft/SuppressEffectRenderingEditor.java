package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;
import java.util.Arrays;

public class SuppressEffectRenderingEditor extends Editor {

    public SuppressEffectRenderingEditor() {
        this.switcher = new PatternSwitcher<ItemModel>((item)-> {
            item.setEffectRenderable(false);
        });

        this.switcher.addAllPatterns(Arrays.asList("Bottle o' Enchanting", "Enchanted Book", "minecraft:golden_apple:1",
            "Nether Star", "Written Book"));
    }

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            this.switcher.match(item.getId(), item);
            this.switcher.match(item.getDisplayName(), item);
        }
    }

    // Private Variables ///////////////////////////////////////////////////////////////////////////////////////////////

    private PatternSwitcher<ItemModel> switcher = null;
}
