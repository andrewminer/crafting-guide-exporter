package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.registry.GameData;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class FluidGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        for (String fluidId : FluidRegistry.getRegisteredFluids().keySet()) {
            FluidStack fluidStack = FluidRegistry.getFluidStack(fluidId, DEFAULT_AMOUNT);
            ItemStackModel stackModel = ItemStackModel.convert(fluidStack, modPack);
            ModContainer rawMod = GameData.findModOwner(FluidRegistry.getDefaultFluidName(fluidStack.getFluid()));
            ModModel mod;
            if (rawMod == null) {
                mod = modPack.getMod("minecraft");
            } else {
                mod = modPack.getMod(rawMod.getModId());
            }
            boolean needsToBeRegisteredToMod = true;
            for (ItemModel item : mod.getAllItems()) {
                if (item.getId() == stackModel.getItem().getId()) {
                    needsToBeRegisteredToMod = false;
                    break;
                }
            }
            if (needsToBeRegisteredToMod) {
                mod.addItem(stackModel.getItem());
            }
            modPack.addItem(stackModel.getItem());
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static int DEFAULT_AMOUNT = 1000;
}
