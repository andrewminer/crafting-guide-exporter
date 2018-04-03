package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
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
        this.items = new HashMap<ModModel, Collection<ItemModel>>();
        for (ModModel mod : modPack.getAllMods()) {
            
            if (!mod.isEnabled()) continue;

            this.items.put(mod, mod.getAllItems());
        }
        
        this.screen.dumpItems(modPack, items, dumpAllItemsStep, false);
        
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<ModModel, Collection<ItemModel>> items = null;

    private ItemIconDumperScreen screen = null;
}
