package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.Dumper;
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

public class OreDictionaryDumper extends Dumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        new File(DUMP_DIR).mkdirs();
        File outputFile = new File(DUMP_DIR, DUMP_FILE);
        FileWriter fileWriter = null;
        Printer printer = null;

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
        printer.println("{");
        printer.indent();

        Map<String, List<ItemModel>> dictionary = modPack.getOreDictionary();
        ArrayList<String> keys = new ArrayList<String>(dictionary.keySet());
        keys.sort(null);

        boolean needsDelimiter = false;
        for (String oreName : keys) {
            if (needsDelimiter) printer.println(",");
            needsDelimiter = true;

            printer.println("\"" + oreName + "\": [");
            printer.indent();

            List<ItemModel> items = dictionary.get(oreName);
            boolean needsInnerDelimiter = false;
            for (ItemModel item : items) {
                if (needsInnerDelimiter) printer.println(",");
                needsInnerDelimiter = true;
                printer.print("\"" + item.getDisplayName() + " <" + item.getId() + ">\"");
            }
            printer.println();

            printer.outdent();
            printer.print("]");
        }
        printer.println();

        printer.outdent();
        printer.println("}");
    }
}
