package com.craftingguide.exporter;

import com.craftingguide.CraftingGuideConfig;
import com.craftingguide.CraftingGuideException;
import com.craftingguide.CraftingGuideFileManager;
import com.craftingguide.exporter.commands.CraftingGuideDumpCommand;
import com.craftingguide.exporter.extensions.buildcraft.BuildCraftExtension;
import com.craftingguide.exporter.extensions.craftingguide.CraftingGuideExtension;
import com.craftingguide.exporter.extensions.debug.DebugExtension;
import com.craftingguide.exporter.extensions.forge.ForgeExtension;
import com.craftingguide.exporter.extensions.minecraft.MinecraftExtension;
import com.craftingguide.exporter.models.ModPackModel;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import net.minecraftforge.client.ClientCommandHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ExporterMod.MODID, version = ExporterMod.VERSION)
public class ExporterMod implements Registry {

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static final String MODID = "crafting-guide-exporter";

    public static final String VERSION = "1.0";

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void exportCraftingGuideData(AsyncStep commandStep) throws CraftingGuideException {

        try {
            LOGGER.info("Starting CraftingGuide export...");
            long start = System.currentTimeMillis();

            this.executeWorkers(this.gatherers, ()-> {
                this.executeWorkers(this.editors, ()-> {
                    this.executeWorkers(this.dumpers, ()-> {
                        long duration = System.currentTimeMillis() - start;
                        LOGGER.info("Finished CraftingGuide export after " + duration + "ms.");

                        commandStep.done();
                    });
                });
            });
        } catch (Exception e) {
            throw new CraftingGuideException("export failed", e);
        }
    }

    // Forge Event Handlers ////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void init(FMLInitializationEvent event) {
        this.initializeConfigs();

        try {
            ClientCommandHandler.instance.registerCommand(new CraftingGuideDumpCommand(this));

            this.register(new CraftingGuideExtension());
            this.register(new DebugExtension());
            this.register(new ForgeExtension());
            this.register(new MinecraftExtension());
            this.register(new BuildCraftExtension());
        } catch (Exception e) {
            System.err.println("Failed to initialize Crafting Guide Export!");
            e.printStackTrace();
        }

        this.getConfig().save();
    }

    // IRegistry Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public void registerDumper(Dumper dumper) {
        this.registerDumper(dumper, Priority.MEDIUM);
    }

    public void registerDumper(Dumper dumper, Priority priority) {
        this.registerWorker(priority, dumper, this.dumpers);
    }

    public void registerEditor(Editor editor) {
        this.registerEditor(editor, Priority.MEDIUM);
    }

    public void registerEditor(Editor editor, Priority priority) {
        this.registerWorker(priority, editor, this.editors);
    }

    public void registerGatherer(Gatherer gatherer) {
        this.registerGatherer(gatherer, Priority.MEDIUM);
    }

    public void registerGatherer(Gatherer gatherer, Priority priority) {
        this.registerWorker(priority, gatherer, this.gatherers);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public CraftingGuideConfig getConfig() {
        return this.config;
    }

    public CraftingGuideFileManager getFileManager() {
        return this.getFileManager();
    }

    public ModPackModel getModPack() {
        return this.modPack;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger LOGGER = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void executeWorkers(LinkedList<Worker> workers, AsyncStep executeList) {
        if (workers.isEmpty()) {
            executeList.done();
            return;
        }

        Worker worker = workers.removeFirst();
        String workerName = worker.getClass().getSimpleName();

        try {
            long start = System.currentTimeMillis();

            worker.work(this.modPack, ()-> {
                long duration = System.currentTimeMillis() - start;
                LOGGER.info("Executed " + workerName + " in " + duration + "ms.");

                this.executeWorkers(workers, executeList);
            });
        } catch (Exception e) {
            LOGGER.error("Failed to execute " + workerName, e);
            this.executeWorkers(workers, executeList);
        }
    }

    private void executeWorkers(Map<Priority, List<Worker>> workerMap, AsyncStep executeList) {
        LinkedList<Worker> allWorkers = new LinkedList<Worker>();

        for (Priority priority : Priority.values()) {
            List<Worker> workers = workerMap.get(priority);
            if (workers == null) continue;

            allWorkers.addAll(workers);
        }

        this.executeWorkers(allWorkers, executeList);
    }

    private void initializeConfigs() {
        this.fileManager = new CraftingGuideFileManager();
        this.config = new CraftingGuideConfig(MODID, this.fileManager);

        this.fileManager.setDumpDir(this.config.getOutputDir());
    }

    private void register(ExporterExtension extension) {
        extension.register(this);
    }

    private void registerWorker(Priority priority, Worker worker, Map<Priority, List<Worker>> workerMap) {
        List<Worker> workers = workerMap.get(priority);
        if (workers == null) {
            workers = new ArrayList<Worker>();
            workerMap.put(priority, workers);
        }
        workers.add(worker);

        worker.setConfig(this.getConfig());
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Map<Priority, List<Worker>> dumpers   = new TreeMap<>();
    private Map<Priority, List<Worker>> editors   = new TreeMap<>();
    private Map<Priority, List<Worker>> gatherers = new TreeMap<>();

    private CraftingGuideConfig config = null;

    private CraftingGuideFileManager fileManager = null;

    private ModPackModel modPack = new ModPackModel();
}
