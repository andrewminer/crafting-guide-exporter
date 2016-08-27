package com.craftingguide.exporter.baseWorkers;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.PatternSwitcher;

public class ItemRemoverEditor implements IEditor {

    public ItemRemoverEditor(Iterable<String> exclusionPatterns) {
        this._switcher = new PatternSwitcher<>();
        for (String patternText : exclusionPatterns) {
            this._switcher.addPattern(patternText);
        }
    }

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        this._switcher.setDefaultConsumer((item) -> {
            modPack.removeItem(item.id);
        });

        for (ItemModel item : modPack.getAllItems()) {
            this._switcher.match(item.id, item);
            this._switcher.match(item.displayName, item);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private PatternSwitcher<ItemModel> _switcher = null;
}
