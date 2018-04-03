package com.craftingguide.exporter.extensions.bigreactors;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.MultiblockRecipe;
import net.minecraft.item.ItemStack;
import erogenousbeef.bigreactors.common.BigReactors;

public class BigReactorsMultiblockGatherer extends Gatherer {

    // Gatherer Overrides //////////////////////////////////////////////////////////////////////////////////////////////

    public void gather(ModPackModel modPack) {
        modPack.addItem(this.makeSmallPassiveReactor(modPack));
        modPack.addItem(this.makeMediumPassiveReactor(modPack));
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String ACCESS_PORT_ID = "BigReactors:BRReactorPart:4";

    private static String BIG_REACTORS_MOD_ID = "BigReactors";

    private static String FLUID_REDSTONE_ID = "ThermalFoundation:FluidRedstone";

    private static String REACTOR_CASING_ID = "BigReactors:BRReactorPart:0";

    private static String REACTOR_CONTROL_ROD_ID = "BigReactors:BRReactorPart:2";

    private static String REACTOR_CONTROLLER_ID = "BigReactors:BRReactorPart:1";

    private static String REACTOR_GLASS_ID = "BigReactors:BRMultiblockGlass:0";

    private static String REACTOR_POWER_TAP_ID = "BigReactors:BRReactorPart:3";

    private static String YELLORIUM_FUEL_ROD_ID = "BigReactors:YelloriumFuelRod";

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private ItemModel makeMediumPassiveReactor(ModPackModel modPack) {
        ItemStackModel a = new ItemStackModel(modPack.getItem(ACCESS_PORT_ID), 1);
        ItemStackModel s = new ItemStackModel(modPack.getItem(REACTOR_CASING_ID), 1);
        ItemStackModel r = new ItemStackModel(modPack.getItem(REACTOR_CONTROL_ROD_ID), 1);
        ItemStackModel c = new ItemStackModel(modPack.getItem(REACTOR_CONTROLLER_ID), 1);
        ItemStackModel p = new ItemStackModel(modPack.getItem(REACTOR_POWER_TAP_ID), 1);
        ItemStackModel y = new ItemStackModel(modPack.getItem(YELLORIUM_FUEL_ROD_ID), 1);
        ItemStackModel g = new ItemStackModel(modPack.getItem(REACTOR_GLASS_ID), 1);
        ItemStackModel o = new ItemStackModel(modPack.getItem(FLUID_REDSTONE_ID), 1000);

        MultiblockRecipe recipe = new MultiblockRecipe();
        recipe.setItemStacks(new ItemStackModel[][][]
            {
                {
                    { s, s, s, s, s },
                    { s, s, s, s, s },
                    { s, s, s, s, s },
                    { s, s, s, s, s },
                    { s, s, s, s, s } },
                {
                    { s, g, a, g, s },
                    { g, y, o, y, g },
                    { c, o, y, o, p },
                    { g, y, o, y, g },
                    { s, g, a, g, s } },
                {
                    { s, g, g, g, s },
                    { g, y, o, y, g },
                    { g, o, y, o, g },
                    { g, y, o, y, g },
                    { s, g, g, g, s } },
                {
                    { s, g, g, g, s },
                    { g, y, o, y, g },
                    { g, o, y, o, g },
                    { g, y, o, y, g },
                    { s, g, g, g, s } },
                {
                    { s, s, s, s, s },
                    { s, r, s, r, s },
                    { s, s, r, s, s },
                    { s, r, s, r, s },
                    { s, s, s, s, s } } });

        String id = BIG_REACTORS_MOD_ID + ":multiblock-medium-passive-reactor";
        ItemModel item = new ItemModel(id, "Medium Passive Reactor");
        item.setGroupName("Reactors");
        item.setMultiblock(recipe);
        item.setItemStackIcon(new ItemStack(BigReactors.blockReactorPart, 1, 1));
        return item;
    }

    private ItemModel makeSmallPassiveReactor(ModPackModel modPack) {
        ItemStackModel a = new ItemStackModel(modPack.getItem(ACCESS_PORT_ID), 1);
        ItemStackModel s = new ItemStackModel(modPack.getItem(REACTOR_CASING_ID), 1);
        ItemStackModel r = new ItemStackModel(modPack.getItem(REACTOR_CONTROL_ROD_ID), 1);
        ItemStackModel c = new ItemStackModel(modPack.getItem(REACTOR_CONTROLLER_ID), 1);
        ItemStackModel p = new ItemStackModel(modPack.getItem(REACTOR_POWER_TAP_ID), 1);
        ItemStackModel y = new ItemStackModel(modPack.getItem(YELLORIUM_FUEL_ROD_ID), 1);

        MultiblockRecipe recipe = new MultiblockRecipe();
        recipe.setItemStacks(new ItemStackModel[][][]
            {
                {
                    { s, s, s },
                    { s, s, s },
                    { s, s, s } },
                {
                    { s, a, s },
                    { c, y, p },
                    { s, a, s } },
                {
                    { s, s, s },
                    { s, r, s },
                    { s, s, s } } });

        String id = BIG_REACTORS_MOD_ID + ":multiblock-small-passive-reactor";
        ItemModel item = new ItemModel(id, "Small Passive Reactor");
        item.setGroupName("Reactors");
        item.setMultiblock(recipe);
        item.setItemStackIcon(new ItemStack(BigReactors.blockReactorPart, 1, 1));
        return item;
    }
}
