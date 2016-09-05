package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.IOException;

public class ModDumper extends AbstractFileDumper {

    // AbstractFileDumper Methods //////////////////////////////////////////////////////////////////////////////////////

    protected void dump(ModPackModel modPack, Printer printer) throws IOException {
        for (ModModel mod : modPack.getAllMods()) {
            String format = "%1$-39s    %2$-39s    %3$-20s    %4$-10s";
            String enabledText = mod.isEnabled() ? "enabled" : "";
            String text = String.format(format, mod.getId(), mod.getDisplayName(), mod.getVersion(), enabledText);
            printer.println(text);
        }
    }

    protected String getDumpDir() {
        return "./dumps/debug";
    }

    protected String getDumpFile() {
        return "mods.txt";
    }
}
