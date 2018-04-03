package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.Slugifier;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModDumper extends AbstractCraftingGuideDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack) {
        for (ModModel mod : modPack.getAllMods()) {
            if (!mod.isEnabled()) continue;

            FileWriter fileWriter = null;
            Printer printer = null;

            new File(this.getFileManager().getModDir(mod)).mkdirs();
            String modFile = this.getFileManager().getModFile(mod);

            try {
                fileWriter = new FileWriter(modFile);
                printer = new Printer(fileWriter);

                printer.println("schema: 1");
                printer.println();
                printer.println("name: " + mod.getDisplayName());
                if (mod.getAuthor() != null) {
                    printer.println("author: " + mod.getAuthor());
                }
                if (mod.getDescription() != null) {
                    printer.println("description: " + mod.getDescription());
                }
                if (mod.getUrl() != null) {
                    printer.println("homePageUrl: " + mod.getUrl());
                }
                printer.println();
                printer.println("version: " + this.slugifier.slugify(mod.getVersion()));
            } catch (IOException e) {
                LOGGER.error("Failed to write " + modFile, e);
            } finally {
                try {
                    fileWriter.close();
                } catch (Exception e) {}
            }
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Slugifier slugifier = new Slugifier();
}
