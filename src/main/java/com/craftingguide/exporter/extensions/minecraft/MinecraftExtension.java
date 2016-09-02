package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;
import com.craftingguide.exporter.IRegistry.Priority;

public class MinecraftExtension implements IExtension {

    // IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

    public void register(IRegistry registry) {
        registry.registerGatherer(new ItemGatherer(), Priority.HIGHEST);

        registry.registerGatherer(new ShapedRecipeGatherer());
        registry.registerGatherer(new ShapelessRecipeGatherer());
        registry.registerGatherer(new FurnaceRecipeGatherer());
        registry.registerGatherer(new PotionRecipeGatherer());

        registry.registerEditor(new RemoveUncraftableEditor());
        registry.registerEditor(new DisambiguateDisplayNameEditor());
        registry.registerEditor(new AddCraftingTableEditor());
        registry.registerEditor(new ModEditor());
        registry.registerEditor(new AddContainerItemEditor(), Priority.LOW);
    }
}
