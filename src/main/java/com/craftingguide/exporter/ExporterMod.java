package com.craftingguide.exporter;

import com.craftingguide.CraftingGuideException;
import com.craftingguide.exporter.commands.CraftingGuideDumpCommand;
import com.craftingguide.exporter.extensions.craftingguide.CraftingGuideExtension;
import com.craftingguide.exporter.extensions.debug.DebugExtension;
import com.craftingguide.exporter.extensions.minecraft.MinecraftExtension;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import java.util.ArrayList;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExporterMod.MODID, version = ExporterMod.VERSION)
public class ExporterMod implements IRegistry {

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String MODID = "crafting-guide-exporter";

    public static final String VERSION = "1.0";

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void exportCraftingGuideData() throws CraftingGuideException {

        try {
            logger.info("Starting CraftingGuide export...");
            long start = System.currentTimeMillis();

            this.modPack.gatherMods();
            this.modPack.gatherItems();
            this.modPack.gatherOreDictionary();

            for (IGatherer gatherer : this.gatherers) {
                gatherer.gather(this.modPack);
            }

            for (IEditor editor : this.editors) {
                editor.edit(this.modPack);
            }

            for (IDumper dumper : this.dumpers) {
                dumper.dump(this.modPack);
            }

            long duration = System.currentTimeMillis() - start;
            logger.info("Finished CraftingGuide export after " + duration + "ms.");
        } catch (Throwable e) {
            throw new CraftingGuideException("export failed", e);
        }
    }

    // Forge Event Handlers ////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            ClientCommandHandler.instance.registerCommand(new CraftingGuideDumpCommand(this));

            this.register(new DebugExtension());
            this.register(new CraftingGuideExtension());
            this.register(new MinecraftExtension());
        } catch (Throwable e) {
            System.err.println("Failed to initialize Crafting Guide Export!");
            e.printStackTrace();
        }
    }

    // IRegistry Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void registerDumper(IDumper dumper) {
        this.dumpers.add(dumper);
    }

    public void registerEditor(IEditor editor) {
        this.editors.add(editor);
    }

    public void registerGatherer(IGatherer gatherer) {
        this.gatherers.add(gatherer);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ModPackModel getModPack() {
        return this.modPack;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void register(IExtension extension) {
        this.extensions.add(extension);
        extension.register(this);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ArrayList<IDumper>    dumpers    = new ArrayList<IDumper>();
    private ArrayList<IEditor>    editors    = new ArrayList<IEditor>();
    private ArrayList<IExtension> extensions = new ArrayList<IExtension>();
    private ArrayList<IGatherer>  gatherers  = new ArrayList<IGatherer>();
    private ModPackModel          modPack    = new ModPackModel();
}
