package com.craftingguide.exporter;

import com.craftingguide.CraftingGuideException;
import com.craftingguide.exporter.commands.CraftingGuideDumpCommand;
import com.craftingguide.exporter.extensions.craftingguide.CraftingGuideExtension;
import com.craftingguide.exporter.extensions.debug.DebugExtension;
import com.craftingguide.exporter.extensions.forge.ForgeExtension;
import com.craftingguide.exporter.extensions.minecraft.MinecraftExtension;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

            this.executeWorkers(this.gatherers);
            this.executeWorkers(this.editors);
            this.executeWorkers(this.dumpers);

            long duration = System.currentTimeMillis() - start;
            logger.info("Finished CraftingGuide export after " + duration + "ms.");
        } catch (Exception e) {
            throw new CraftingGuideException("export failed", e);
        }
    }

    // Forge Event Handlers ////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void init(FMLInitializationEvent event) {
        try {
            ClientCommandHandler.instance.registerCommand(new CraftingGuideDumpCommand(this));

            this.register(new CraftingGuideExtension());
            this.register(new DebugExtension());
            this.register(new ForgeExtension());
            this.register(new MinecraftExtension());
        } catch (Exception e) {
            System.err.println("Failed to initialize Crafting Guide Export!");
            e.printStackTrace();
        }
    }

    // IRegistry Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void registerDumper(IDumper dumper) {
        this.registerDumper(dumper, Priority.MEDIUM);
    }

    public void registerDumper(IDumper dumper, Priority priority) {
        this.registerWorker(priority, dumper, this.dumpers);
    }

    public void registerEditor(IEditor editor) {
        this.registerEditor(editor, Priority.MEDIUM);
    }

    public void registerEditor(IEditor editor, Priority priority) {
        this.registerWorker(priority, editor, this.editors);
    }

    public void registerGatherer(IGatherer gatherer) {
        this.registerGatherer(gatherer, Priority.MEDIUM);
    }

    public void registerGatherer(IGatherer gatherer, Priority priority) {
        this.registerWorker(priority, gatherer, this.gatherers);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ModPackModel getModPack() {
        return this.modPack;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void executeWorkers(Map<Priority, List<IWorker>> workerMap) {
        for (Priority priority : Priority.values()) {
            List<IWorker> workers = workerMap.get(priority);
            if (workers == null) continue;

            for (IWorker worker : workers) {
                String workerName = worker.getClass().getSimpleName();
                try {
                    long start = System.currentTimeMillis();

                    if (worker instanceof IDumper) {
                        ((IDumper) worker).dump(this.modPack);
                    } else if (worker instanceof IEditor) {
                        ((IEditor) worker).edit(this.modPack);
                    } else if (worker instanceof IGatherer) {
                        ((IGatherer) worker).gather(this.modPack);
                    }

                    long duration = System.currentTimeMillis() - start;
                    logger.info("Executed " + workerName + " in " + duration + "ms.");
                } catch (Exception e) {
                    logger.error("Failed to execute " + workerName, e);
                }
            }
        }
    }

    private void register(IExtension extension) {
        extension.register(this);
    }

    private void registerWorker(Priority priority, IWorker worker, Map<Priority, List<IWorker>> workerMap) {
        List<IWorker> workers = workerMap.get(priority);
        if (workers == null) {
            workers = new ArrayList<IWorker>();
            workerMap.put(priority, workers);
        }
        workers.add(worker);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<Priority, List<IWorker>> dumpers   = new TreeMap<>();
    private Map<Priority, List<IWorker>> editors   = new TreeMap<>();
    private Map<Priority, List<IWorker>> gatherers = new TreeMap<>();
    private ModPackModel                 modPack   = new ModPackModel();
}
