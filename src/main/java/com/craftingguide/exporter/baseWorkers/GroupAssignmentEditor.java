package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;

public class GroupAssignmentEditor implements IEditor {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPattern(String group, String pattern) {
        this.switcher.addPattern(pattern, (item)-> {
            item.groupName = group;
        });
    }

    // IEditor Overrides ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            this.switcher.match(item.displayName, item);
            this.switcher.match(item.id, item);
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private PatternSwitcher<ItemModel> switcher = new PatternSwitcher<ItemModel>();
}
