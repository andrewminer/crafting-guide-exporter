package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;
import com.craftingguide.exporter.extensions.minecraft.ShapelessRecipeGatherer;

public class MinecraftExtension implements IExtension {

	// IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

	public void register(IRegistry registry) {
		registry.registerGatherer(new ShapelessRecipeGatherer());
	}
}
