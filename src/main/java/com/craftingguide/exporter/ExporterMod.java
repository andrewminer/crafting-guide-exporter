package com.craftingguide.exporter;

import java.util.ArrayList;

import com.craftingguide.exporter.extensions.craftingguide.CraftingGuideExtension;
import com.craftingguide.exporter.extensions.minecraft.MinecraftExtension;
import com.craftingguide.exporter.models.ModPackModel;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = ExporterMod.MODID, version = ExporterMod.VERSION)
public class ExporterMod implements IRegistry {

    public static final String MODID   = "crafting-guide-exporter";
    public static final String VERSION = "1.0";

	private ArrayList<IDumper>    _dumpers    = null;
	private ArrayList<IExtension> _extensions = null;
	private ArrayList<IGatherer>  _gatherers  = null;
	private ModPackModel          _modPack    = null;

	// FML Event Handlers //////////////////////////////////////////////////////////////////////////////////////////////

    @EventHandler
    public void init(FMLInitializationEvent event) {
		this._dumpers    = new ArrayList<IDumper>();
		this._extensions = new ArrayList<IExtension>();
		this._gatherers  = new ArrayList<IGatherer>();
		this._modPack    = new ModPackModel();

		this._createExtensions();

		System.out.println("Crafting Guide Exporter is now ready");
    }

	@EventHandler
	public void onPostInit(FMLPostInitializationEvent event) {
		try {
			System.out.println("Starting CraftingGuide export...");
			
			this._modPack.gatherItems();
	
			for (IGatherer gatherer : this._gatherers) {
				gatherer.gather(this._modPack);
			}
	
			for (IDumper dumper : this._dumpers) {
				dumper.dump(this._modPack);
			}
			
			System.out.println("Finished CraftingGuide export.");
		} catch (Throwable e) {
			System.err.println("Failed to process CraftingGuide export!");
			e.printStackTrace();
		}
	}

	// IRegistry Methods ///////////////////////////////////////////////////////////////////////////////////////////////

	public void registerDumper(IDumper dumper) {
		this._dumpers.add(dumper);
	}

	public void registerGatherer(IGatherer gatherer) {
		this._gatherers.add(gatherer);
	}

	// Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

	private void _createExtensions() {
		this._extensions.add(new CraftingGuideExtension());
		this._extensions.add(new MinecraftExtension());
	}
}
