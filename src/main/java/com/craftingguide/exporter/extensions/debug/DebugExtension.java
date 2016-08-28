package com.craftingguide.exporter.extensions.debug;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;

public class DebugExtension implements IExtension {

    // IExtension Methods //////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(IRegistry registry) {
        registry.registerDumper(new ModDumper());
        registry.registerDumper(new ItemDumper());
        registry.registerDumper(new OreDictionaryDumper());
    }
}
