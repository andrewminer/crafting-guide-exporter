package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        for (String fluidId : FluidRegistry.getRegisteredFluids().keySet()) {
            FluidStack fluidStack = FluidRegistry.getFluidStack(fluidId, DEFAULT_AMOUNT);
            ItemStackModel stackModel = ItemStackModel.convert(fluidStack, modPack);
            modPack.addItem(stackModel.getItem());
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int DEFAULT_AMOUNT = 1000;
}
