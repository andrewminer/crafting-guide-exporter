package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OreDictionaryDumper implements IDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        new File(DUMP_DIR).mkdirs();
        File outputFile = new File(DUMP_DIR, DUMP_FILE);
        FileWriter fileWriter = null;
        Printer printer = null;

        logger.info("Writing ore dictionary to: " + DUMP_DIR + "/" + DUMP_FILE);
        try {
            fileWriter = new FileWriter(outputFile, false);
            printer = new Printer(fileWriter);

            this.printOreDictionary(modPack, printer);
        } catch (IOException e) {
            logger.error("Could not write to " + outputFile + ": ", e);
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String DUMP_DIR = "./dumps/debug";

    private static String DUMP_FILE = "ore-dictionary.json";

    private static Logger logger = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void printOreDictionary(ModPackModel modPack, Printer printer) throws IOException {
        printer.line("{");
        printer.indent();

        Map<String, List<ItemModel>> dictionary = modPack.getOreDictionary();
        ArrayList<String> keys = new ArrayList<String>(dictionary.keySet());
        keys.sort(null);

        boolean needsDelimiter = false;
        for (String oreName : keys) {
            if (needsDelimiter) printer.line(",");
            needsDelimiter = true;

            printer.line("\"" + oreName + "\": [");
            printer.indent();

            List<ItemModel> items = dictionary.get(oreName);
            boolean needsInnerDelimiter = false;
            for (ItemModel item : items) {
                if (needsInnerDelimiter) printer.line(",");
                needsInnerDelimiter = true;
                printer.text("\"" + item.displayName + " <" + item.id + ">\"");
            }
            printer.line();

            printer.outdent();
            printer.text("]");
        }
        printer.line();

        printer.outdent();
        printer.line("}");
    }
}
