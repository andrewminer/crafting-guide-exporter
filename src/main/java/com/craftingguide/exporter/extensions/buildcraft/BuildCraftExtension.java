package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class BuildCraftExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("buildcraft.GateGatherer");
        registry.registerWorker("buildcraft.RedstoneBoardGatherer");
        registry.registerWorker("buildcraft.RobotGatherer");
        registry.registerWorker("buildcraft.AssemblyTableRecipeGatherer");

        registry.registerWorker("buildcraft.BuildCraftModEditor");
        registry.registerWorker("buildcraft.BuildCraftGroupEditor");
    }
}
