package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractFileDumper implements IDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        String dumpDir = this.getDumpDir();
        String dumpFile = this.getDumpFile();

        new File(dumpDir).mkdirs();
        File outputFile = new File(dumpDir, dumpFile);
        FileWriter fileWriter = null;
        Printer printer = null;

        try {
            fileWriter = new FileWriter(outputFile, false);
            printer = new Printer(fileWriter);

            this.dump(modPack, printer);
        } catch (IOException e) {
            logger.error("Could not write to " + outputFile + ": ", e);
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    // Abstract Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    protected abstract void dump(ModPackModel modPack, Printer printer) throws IOException;

    protected abstract String getDumpDir();

    protected abstract String getDumpFile();

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();
}
