package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.registry.GameData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        for (String fluidId : FluidRegistry.getRegisteredFluids().keySet()) {
            FluidStack fluidStack = FluidRegistry.getFluidStack(fluidId, DEFAULT_AMOUNT);
            ItemStackModel stackModel = ItemStackModel.convert(fluidStack, modPack);
            if (fluidStack.getFluid().getBlock() != null) {
                ModModel mod = modPack.getMod(GameData.findModOwner(GameData.blockRegistry.getNameForObject(fluidStack.getFluid().getBlock())).getModId());
                for (ItemModel item : mod.getAllItems()) {
                    if (item.getId() == stackModel.getItem().getId()) {
                        mod.addItem(stackModel.getItem());
                    }
                }
            }
            modPack.addItem(stackModel.getItem());
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int DEFAULT_AMOUNT = 1000;
}
