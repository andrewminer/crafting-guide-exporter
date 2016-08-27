package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModVersionDumper implements IDumper {

	// IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

	public void dump(ModPackModel modPack) {
		new File("crafting-guide").mkdirs();
		File outputFile = new File("crafting-guide", "output.cg");
		FileWriter writer = null;

		try {
			writer = new FileWriter(outputFile, false);

			for (ItemModel item : modPack.getAllItems()) {
				writer.write("item: " + item.displayName + "\n\n");
			}
		} catch (IOException e) {
			System.err.println("Could not write to " + outputFile + ": " + e);
		} finally {
			try {
				if (writer != null) { writer.close(); }
			} catch (IOException e) {
				// do nothing
			}
		}
	}
}
