package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class MinecraftExtension implements ExporterExtension {

    // IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

    public void register(Registry registry) {
        registry.registerGatherer(new ItemGatherer(), Priority.HIGH);

        registry.registerGatherer(new ShapedRecipeGatherer(), Priority.LOW);
        registry.registerGatherer(new ShapelessRecipeGatherer(), Priority.LOW);

        registry.registerGatherer(new FurnaceRecipeGatherer(), Priority.LOWEST);
        registry.registerGatherer(new PotionRecipeGatherer(), Priority.LOWEST);

        registry.registerEditor(new AddCraftingTableEditor());
        registry.registerEditor(new DisambiguateDisplayNameEditor());
        registry.registerEditor(new MinecraftGatherableEditor());
        registry.registerEditor(new MinecraftGroupEditor());
        registry.registerEditor(new MinecraftModEditor());
        registry.registerEditor(new RemoveUncraftableEditor());

        registry.registerEditor(new AddContainerItemEditor(), Priority.LOW);
    }
}
