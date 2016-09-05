package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;

public class GatherableEditor extends Editor {

    public GatherableEditor() {
        this.switcher = new PatternSwitcher<ItemModel>((item)-> {
            item.setGatherable(true);
        });
    }

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            this.switcher.match(item.getDisplayName(), item);
            this.switcher.match(item.getId(), item);
        }
    }

    // Protected Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    protected void addPattern(String patternText) {
        this.switcher.addPattern(patternText);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private PatternSwitcher<ItemModel> switcher = null;
}
