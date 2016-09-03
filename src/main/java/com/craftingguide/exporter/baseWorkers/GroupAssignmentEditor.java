package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;

public class GroupAssignmentEditor extends Editor {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPattern(String group, String pattern) {
        this.switcher.addPattern(pattern, (item)-> {
            item.setGroupName(group);
        });
    }

    // IEditor Overrides ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            this.switcher.match(item.getDisplayName(), item);
            this.switcher.match(item.getId(), item);
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private PatternSwitcher<ItemModel> switcher = new PatternSwitcher<ItemModel>();
}
