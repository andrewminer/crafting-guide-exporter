package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class BuildCraftExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerGatherer(new GateGatherer());
        registry.registerGatherer(new RedstoneBoardGatherer());
        registry.registerGatherer(new RobotGatherer());

        registry.registerEditor(new BuildCraftModEditor());
        registry.registerEditor(new BuildCraftGroupEditor());
    }
}
