package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;

public class GroupAssignmentEditor extends Editor {

    public GroupAssignmentEditor(String modId) {
        this.setModId(modId);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void addPattern(String group, String pattern) {
        this.switcher.addPattern(pattern, (item)-> {
            item.setGroupName(group);
        });
    }

    // Property methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getModId() {
        return this.modId;
    }

    public void setModId(String modId) {
        if (modId == null) throw new IllegalArgumentException("modId cannot be null");
        this.modId = modId;
    }

    // IEditor Overrides ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            if (!item.isFromMod(this.getModId())) return;

            this.switcher.match(item.getDisplayName(), item);
            this.switcher.match(item.getId(), item);
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String modId = null;

    private PatternSwitcher<ItemModel> switcher = new PatternSwitcher<ItemModel>();
}
