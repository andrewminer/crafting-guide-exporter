package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ItemIconDumper extends AbstractCraftingGuideDumper {

    public ItemIconDumper(CraftingGuideFileManager fileManager) {
        super(fileManager);
    }

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack) {
        Map<String, List<ItemModel>> itemsByMod = modPack.getItemsByMod();
        for (String modId : itemsByMod.keySet()) {
            ModModel mod = modPack.getMod(modId);
            this.dumpMod(mod, itemsByMod.get(modId));
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void dumpMod(ModModel mod, List<ItemModel> items) {
        for (ItemModel item : items) {
            this.dumpItemIcon(mod, item);
        }
    }

    private void dumpItemIcon(ModModel mod, ItemModel item) {
        if (!this.getFileManager().ensureDir(this.getFileManager().getItemDir(mod, item))) return;

        String iconFile = this.getFileManager().getItemIconFile(mod, item);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(iconFile);
            stream.write(this.produceItemIconData(item));
        } catch (IOException e) {
            System.err.println("Could not write icon file to: " + iconFile);
        } finally {
            try {
                stream.close();
            } catch (IOException e) {}
        }
    }

    private byte[] produceItemIconData(ItemModel item) {
        // TODO: Actually render the item.
        return new byte[] {};
    }
}
