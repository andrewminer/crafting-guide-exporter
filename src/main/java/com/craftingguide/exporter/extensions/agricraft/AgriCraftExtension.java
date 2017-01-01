package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;

public class AgriCraftExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("agricraft.MutationRecipeGatherer");
        registry.registerWorker("agricraft.FruitGatherer");

        registry.registerWorker("agricraft.AgriCraftGroupEditor");
        registry.registerWorker("agricraft.AgriCraftModEditor");
        registry.registerWorker("agricraft.AgriCraftItemRemoverEditor");
        registry.registerWorker("agricraft.WoodenItemEditor");
        registry.registerWorker("agricraft.WoodenItemRecipeEditor");
    }
}
