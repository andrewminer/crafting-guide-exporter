package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.ExporterExtension;
import com.craftingguide.exporter.Registry;
import com.craftingguide.exporter.Registry.Priority;

public class CofhExtension implements ExporterExtension {

    // ExporterExtension Overrides /////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void register(Registry registry) {
        registry.registerWorker("cofh.CrucibleRecipeGatherer");
        registry.registerWorker("cofh.ExtruderRecipeGatherer");
        registry.registerWorker("cofh.InsolatorRecipeGatherer");
        registry.registerWorker("cofh.PrecipitatorRecipeGatherer");
        registry.registerWorker("cofh.PulverizerRecipeGatherer");
        registry.registerWorker("cofh.RedstoneFurnaceRecipeGatherer");
        registry.registerWorker("cofh.SawmillRecipeGatherer");
        registry.registerWorker("cofh.SmelterRecipeGatherer");
        registry.registerWorker("cofh.TransposerRecipeGatherer");

        registry.registerWorker("cofh.TieredMachineEditor", Priority.HIGH);

        registry.registerWorker("cofh.CofhModEditor");
        registry.registerWorker("cofh.FlorbEditor");
        registry.registerWorker("cofh.IgnoreOddIngredientEditor");
        registry.registerWorker("cofh.IgnoreUselessPulverizerRecipeEditor");
        registry.registerWorker("cofh.SuppressEffectRenderingEditor");
        registry.registerWorker("cofh.ThermalExpansionGroupEditor");
        registry.registerWorker("cofh.ThermalFoundationGroupEditor");
    }
}
