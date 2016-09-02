package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;
import com.craftingguide.exporter.IRegistry.Priority;

public class ForgeExtension implements IExtension {

    @Override
    public void register(IRegistry registry) {
        registry.registerGatherer(new OreDictionaryGatherer(), Priority.HIGH);
        registry.registerGatherer(new ModGatherer());
    }
}
