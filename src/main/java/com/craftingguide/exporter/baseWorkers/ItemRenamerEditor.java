package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.HashMap;
import java.util.Map;

public class ItemRenamerEditor implements IEditor {

    public ItemRenamerEditor(Map<String, String> mappings) {
        this._mappings = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : mappings.entrySet()) {
            this._mappings.put(entry.getKey(), entry.getValue());
        }
    }

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            String newDisplayName = this._mappings.get(item.id);
            if (newDisplayName != null) {
                item.displayName = newDisplayName;
            }
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, String> _mappings = null;
}
