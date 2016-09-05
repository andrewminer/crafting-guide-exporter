package com.craftingguide.exporter.extensions.craftingguide;

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
            if (mod.isEmpty()) continue;

            FileWriter fileWriter = null;
            Printer printer = null;

            new File(this.getFileManager().getModDir(mod)).mkdirs();
            String modFile = this.getFileManager().getModFile(mod);

            try {
                fileWriter = new FileWriter(modFile);
                printer = new Printer(fileWriter);

                printer.line("schema: 1");
                printer.line();
                printer.line("name: " + mod.getDisplayName());
                printer.line("author: " + mod.getAuthor());
                printer.line("description: " + mod.getDescription());
                printer.line("homePageUrl: " + mod.getUrl());
                printer.line();
                printer.line("version: " + mod.getVersion());
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
}
