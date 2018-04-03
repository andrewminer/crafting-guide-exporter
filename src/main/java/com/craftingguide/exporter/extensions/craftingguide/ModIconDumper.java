package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.AsyncStep;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModIconDumper extends AbstractCraftingGuideDumper {

    public ModIconDumper() {
        this.screen = new ItemIconDumperScreen((mod, item, image)-> {
            try {
                CraftingGuideFileManager fm = this.getFileManager();
                if (!fm.ensureDir(fm.getModDir(mod))) return;
                File iconFile = new File(fm.getModIconFile(mod));
                ImageIO.write(image, "png", iconFile);
            } catch (IOException e) {
                LOGGER.error("Failed to save icon for " + mod.getDisplayName(), e);
            }
        });
        this.screen.setIconSize(MOD_ICON_SIZE);
    }

    // Dumper Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack, AsyncStep dumpModIconsStep) {
        this.setModPack(modPack);
        LinkedList<ModModel> mods = new LinkedList<>(modPack.getAllMods());
        
        if (mods.isEmpty()) {
            dumpModIconsStep.done();
            return;
        }

        Map<ModModel, Collection<ItemModel>> modsMap = new HashMap<ModModel, Collection<ItemModel>>();

        while (!mods.isEmpty()) {
            ModModel mod = mods.removeFirst();
            if (mod.isEnabled()) {

                ItemModel iconicBlock = mod.getIconicBlock();
                if (iconicBlock == null) {
                    iconicBlock = this.getModPack().getItem(DEFAULT_ICONIC_BLOCK);
                }
                
                modsMap.put(mod, Arrays.asList(iconicBlock));

            }
        }

        this.screen.dumpItems(modPack, modsMap, dumpModIconsStep, true);
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

    private static Logger LOGGER = LogManager.getLogger();

    private static String DEFAULT_ICONIC_BLOCK = "minecraft:stone";

    private static int MOD_ICON_SIZE = 160;

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ModPackModel modPack = null;

    private ItemIconDumperScreen screen = null;
}
