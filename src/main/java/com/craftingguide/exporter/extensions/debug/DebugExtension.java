package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class DebugExtension implements ExporterExtension {

    // IExtension Methods //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerDumper(new ModDumper());
        registry.registerDumper(new ItemDumper());
        registry.registerDumper(new OreDictionaryDumper());
    }
}
