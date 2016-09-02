package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.IOException;

public class ModDumper extends AbstractFileDumper {

    // AbstractFileDumper Methods //////////////////////////////////////////////////////////////////////////////////////

    protected void dump(ModPackModel modPack, Printer printer) throws IOException {
        for (ModModel mod : modPack.getAllMods()) {
            printer.line(String.format("%1$-31s    %2$-31s    %3$-10s", mod.id, mod.displayName, mod.version));
        }
    }

    protected String getDumpDir() {
        return "./dumps/debug";
    }

    protected String getDumpFile() {
        return "mods.txt";
    }
}
