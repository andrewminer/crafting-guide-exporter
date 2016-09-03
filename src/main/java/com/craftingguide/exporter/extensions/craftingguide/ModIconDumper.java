package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModIconDumper extends AbstractCraftingGuideDumper {

    public ModIconDumper(CraftingGuideFileManager fileManager) {
        super(fileManager);

        this.screen = new ItemIconDumperScreen((mod, item, image)-> {
            try {
                if (!fileManager.ensureDir(fileManager.getModDir(mod))) return;
                File iconFile = new File(fileManager.getModIconFile(mod));
                ImageIO.write(image, "png", iconFile);
            } catch (IOException e) {
                logger.error("Failed to save icon for " + mod.getDisplayName(), e);
            }
        });
        this.screen.setIconSize(MOD_ICON_SIZE);
    }

    // Dumper Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack, AsyncStep dumpModIconsStep) {
        this.setModPack(modPack);
        LinkedList<ModModel> mods = new LinkedList<>(modPack.getAllMods());
        this.processNextMod(mods, dumpModIconsStep);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    private ModPackModel getModPack() {
        return this.modPack;
    }

    private void setModPack(ModPackModel modPack) {
        if (modPack == null) throw new IllegalArgumentException("modPack cannot be null");
        this.modPack = modPack;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

    private static String DEFAULT_ICONIC_BLOCK = "minecraft:grass";

    private static int MOD_ICON_SIZE = 160;

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void processNextMod(LinkedList<ModModel> mods, AsyncStep dumpModIconsStep) {
        if (mods.isEmpty()) {
            dumpModIconsStep.done();
            return;
        }

        ModModel mod = mods.removeFirst();
        ItemModel iconicBlock = mod.getIconicBlock();
        if (iconicBlock == null) {
            iconicBlock = this.getModPack().getItem(DEFAULT_ICONIC_BLOCK);
        }

        this.screen.dumpItems(mod, Arrays.asList(iconicBlock), ()-> {
            this.processNextMod(mods, dumpModIconsStep);
        });
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ModPackModel modPack = null;

    private ItemIconDumperScreen screen = null;
}
