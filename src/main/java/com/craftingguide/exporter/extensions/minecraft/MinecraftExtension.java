package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;

public class MinecraftExtension implements IExtension {

    // IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

    public void register(IRegistry registry) {
        registry.registerEditor(new RemoveUncraftableEditor());
        registry.registerEditor(new DisambiguateDisplayNameEditor());
        registry.registerGatherer(new ShapedRecipeGatherer());
        registry.registerGatherer(new ShapelessRecipeGatherer());
        registry.registerGatherer(new ShapedOreRecipeGatherer());
        registry.registerGatherer(new ShapelessOreRecipeGatherer());
    }
}
