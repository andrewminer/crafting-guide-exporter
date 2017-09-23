package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

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
        this.remainingMods = new LinkedList<ModModel>();
        for (ModModel mod : modPack.getAllMods()) {

            this.remainingMods.add(mod);
        }
        this.processNextMod(dumpAllItemsStep);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void processNextMod(AsyncStep dumpAllItemsStep) {
    	System.out.println(remainingMods.size() + " left");
        if (this.remainingMods == null) return;

        if (this.remainingMods.isEmpty()) {
            this.remainingMods = null;
            dumpAllItemsStep.done();
            return;
        }
        ModModel mod = this.remainingMods.removeFirst();
        this.screen.dumpItems(mod, mod.getAllItems(), ()-> {
            this.processNextMod(dumpAllItemsStep);
        });
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private LinkedList<ModModel> remainingMods = null;

    private ItemIconDumperScreen screen = null;
}
