package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.IOException;

public class ModDumper extends AbstractFileDumper {

    // AbstractFileDumper Methods //////////////////////////////////////////////////////////////////////////////////////

    protected void dump(ModPackModel modPack, Printer printer) throws IOException {
        for (ModModel mod : modPack.getAllMods()) {
            String format = "%1$-31s    %2$-31s    %3$-10s";
            printer.line(String.format(format, mod.getId(), mod.getDisplayName(), mod.getVersion()));
        }
    }

    protected String getDumpDir() {
        return "./dumps/debug";
    }

    protected String getDumpFile() {
        return "mods.txt";
    }
}
