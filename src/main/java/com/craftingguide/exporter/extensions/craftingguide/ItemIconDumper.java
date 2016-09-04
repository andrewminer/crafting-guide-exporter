package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ItemIconDumper extends AbstractCraftingGuideDumper {

    public ItemIconDumper() {
        this.screen = new ItemIconDumperScreen((mod, item, image)-> {
            try {
                CraftingGuideFileManager fm = this.getFileManager();
                if (!fm.ensureDir(fm.getItemDir(mod, item))) return;
                File iconFile = new File(fm.getItemIconFile(mod, item));
                ImageIO.write(image, "png", iconFile);
            } catch (IOException e) {
                LOGGER.error("Failed to save icon for " + item.getDisplayName(), e);
            }
        });
    }

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack, AsyncStep dumpAllItemsStep) {
        this.itemsByModId = modPack.getItemsByMod();

        this.remainingMods = new LinkedList<ModModel>();
        for (ModModel mod : modPack.getAllMods()) {
            List<ItemModel> items = this.itemsByModId.get(mod.getId());
            if (items.isEmpty()) continue;

            this.remainingMods.add(mod);
        }
        this.processNextMod(dumpAllItemsStep);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void processNextMod(AsyncStep dumpAllItemsStep) {
        if (this.remainingMods == null) return;

        if (this.remainingMods.isEmpty()) {
            this.remainingMods = null;
            this.itemsByModId = null;
            dumpAllItemsStep.done();
            return;
        }

        ModModel mod = this.remainingMods.removeFirst();
        List<ItemModel> items = this.itemsByModId.get(mod.getId());

        this.screen.dumpItems(mod, items, ()-> {
            this.processNextMod(dumpAllItemsStep);
        });
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, List<ItemModel>> itemsByModId = null;

    private LinkedList<ModModel> remainingMods = null;

    private ItemIconDumperScreen screen = null;
}
