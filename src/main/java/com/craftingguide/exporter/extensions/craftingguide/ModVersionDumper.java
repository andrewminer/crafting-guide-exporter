package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ModVersionDumper implements IDumper {
	
	private static String DUMP_DIR = "./dumps/crafting-guide";
	
	private static String DUMP_FILE = "mod-version.cg";

	// IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

	public void dump(ModPackModel modPack) {
		new File(DUMP_DIR).mkdirs();
		File outputFile = new File(DUMP_DIR, DUMP_FILE);
		FileWriter fileWriter = null;
		PrintWriter writer = null;

		System.out.println("Writing mod-version.cg file to: " + DUMP_DIR + "/" + DUMP_FILE);
		try {
			fileWriter = new FileWriter(outputFile, false);
			writer = new PrintWriter(fileWriter);

			writer.println("schema: 1");
			writer.println();
			for (ItemModel item : modPack.getAllItems()) {
				writer.println("item: " + item.displayName);
				writer.println("    minecraftId: " + item.id);
				writer.println();
			}
		} catch (IOException e) {
			System.err.println("Could not write to " + outputFile + ": ");
			e.printStackTrace();
		} finally {
			try { fileWriter.close(); } catch (Throwable e) {}
		}
	}
}
