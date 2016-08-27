package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IExtension;
import com.craftingguide.exporter.IRegistry;
import com.craftingguide.exporter.extensions.craftingguide.ModVersionDumper;

public class CraftingGuideExtension implements IExtension {

	// IExtension Methods /////////////////////////////////////////////////////////////////////////////////////////////

	public void register(IRegistry registry) {
		registry.registerDumper(new ModVersionDumper());
	}
}
