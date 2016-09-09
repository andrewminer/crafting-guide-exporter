package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.util.Printer;
import java.io.IOException;

public class FluidDumper extends AbstractFileDumper {

    // AbstractFileDumper Overrides ////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void dump(ModPackModel modPack, Printer printer) throws IOException {
        for (ItemModel item : modPack.getAllItems()) {
            if (!item.isFluid()) continue;
            printer.println(String.format("%1$40s    %2$-40s", item.getDisplayName(), item.getId()));
        }
    }

    @Override
    protected String getDumpDir() {
        return "./dumps/debug";
    }

    @Override
    protected String getDumpFile() {
        return "fluids.txt";
    }

}
