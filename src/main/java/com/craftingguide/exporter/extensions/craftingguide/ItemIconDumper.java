package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

public class ItemIconDumper extends AbstractCraftingGuideDumper {

    public ItemIconDumper(CraftingGuideFileManager fileManager) {
        super(fileManager);

        this.screen = new ItemIconDumperScreen(fileManager);

        MinecraftForge.EVENT_BUS.register(this);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    @SubscribeEvent
    public void onGuiScreenOpened(GuiOpenEvent event) {
        if (this.remainingMods == null) return;

        if (this.screen.isExporting()) {
            // don't allow any other GUI screens to interrupt
            if ((event.gui == null) || (!event.gui.getClass().equals(ItemIconDumperScreen.class))) {
                event.setCanceled(true);
            }
            return;
        }

        this.processNextMod();
    }

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void dump(ModPackModel modPack) {
        this.itemsByModId = modPack.getItemsByMod();
        this.remainingMods = null;
        for (ModModel mod : modPack.getAllMods()) {
            List<ItemModel> items = this.itemsByModId.get(mod.getId());
            if (items.isEmpty()) continue;

            if (this.remainingMods == null) {
                this.remainingMods = new LinkedList<ModModel>();
            }
            this.remainingMods.add(mod);
        }
        this.processNextMod();
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void processNextMod() {
        if (this.remainingMods == null) return;

        if (this.remainingMods.isEmpty()) {
            this.remainingMods = null;
            this.itemsByModId = null;
            return;
        }

        ModModel mod = this.remainingMods.removeFirst();
        List<ItemModel> items = this.itemsByModId.get(mod.getId());

        this.screen.dumpItems(mod, items);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, List<ItemModel>> itemsByModId = null;

    private LinkedList<ModModel> remainingMods = null;

    private ItemIconDumperScreen screen = null;
}
