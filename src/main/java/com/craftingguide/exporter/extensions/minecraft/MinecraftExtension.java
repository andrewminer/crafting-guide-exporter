package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class MinecraftExtension implements ExporterExtension {

    // IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

    public void register(Registry registry) {
        registry.registerWorker("minecraft.ItemGatherer", Priority.HIGH);

        registry.registerWorker("minecraft.ShapedRecipeGatherer", Priority.LOW);
        registry.registerWorker("minecraft.ShapelessRecipeGatherer", Priority.LOW);

        registry.registerWorker("minecraft.FurnaceRecipeGatherer", Priority.LOWEST);
        registry.registerWorker("minecraft.PotionRecipeGatherer", Priority.LOWEST);

        registry.registerWorker("minecraft.AddCraftingTableEditor");
        registry.registerWorker("minecraft.DisambiguateDisplayNameEditor");
        registry.registerWorker("minecraft.MinecraftGatherableEditor");
        registry.registerWorker("minecraft.MinecraftGroupEditor");
        registry.registerWorker("minecraft.MinecraftModEditor");
        registry.registerWorker("minecraft.RemoveUncraftableEditor");
        registry.registerWorker("minecraft.SuppressEffectRenderingEditor");

        registry.registerWorker("minecraft.AddContainerItemEditor", Priority.LOW);
    }
}
