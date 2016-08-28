package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItemDumper implements IDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        new File(DUMP_DIR).mkdirs();
        File outputFile = new File(DUMP_DIR, DUMP_FILE);
        FileWriter fileWriter = null;
        Printer printer = null;

        System.out.println("Writing item list to: " + DUMP_DIR + "/" + DUMP_FILE);
        try {
            fileWriter = new FileWriter(outputFile, false);
            printer = new Printer(fileWriter);

            for (ItemModel item : modPack.getAllItems()) {
                printer.line(String.format("%1$40s    %2$-40s", item.displayName, item.id));
            }
        } catch (IOException e) {
            System.err.println("Could not write to " + outputFile + ": ");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String DUMP_DIR = "./dumps/debug";

    private static String DUMP_FILE = "items.txt";
}
