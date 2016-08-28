package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ModVersionDumper implements IDumper {

    private static String DUMP_DIR = "./dumps/crafting-guide";

    private static String DUMP_FILE = "mod-version.cg";

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        new File(DUMP_DIR).mkdirs();
        File outputFile = new File(DUMP_DIR, DUMP_FILE);
        FileWriter fileWriter = null;
        Printer printer = null;

        System.out.println("Writing mod-version.cg file to: " + DUMP_DIR + "/" + DUMP_FILE);
        try {
            fileWriter = new FileWriter(outputFile, false);
            printer = new Printer(fileWriter);

            printer.line("schema: 1");
            printer.line();
            this.printModPack(modPack, printer);
        } catch (IOException e) {
            System.err.println("Could not write to " + outputFile + ": ");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void printItem(ItemModel item, Printer printer) throws IOException {
        printer.line("item: " + item.displayName);
        printer.indent();
        printer.line("minecraftId: " + item.id);

        if (item.recipes.size() > 0) {
            for (RecipeModel recipe : item.recipes) {
                this.printRecipe(recipe, printer);
            }
        }

        printer.outdent();
        printer.line();
    }

    private void printModPack(ModPackModel modPack, Printer printer) throws IOException {
        for (ItemModel item : modPack.getAllItems()) {
            this.printItem(item, printer);
        }
    }

    private void printRecipe(RecipeModel recipe, Printer printer) throws IOException {
        printer.line("recipe:");
        printer.indent();

        printer.text("inputs: ");
        boolean needsDelimiter = false;
        for (ItemStackModel inputStack : recipe.inputs) {
            if (needsDelimiter) printer.text(", ");
            needsDelimiter = true;

            if (inputStack.quantity > 1) {
                printer.text(inputStack.quantity + " ");
            }
            printer.text(inputStack.item.displayName);
        }
        printer.line();

        printer.line("pattern: " + recipe.getPattern());

        if (recipe.output.quantity > 1) {
            printer.line("quantity: " + recipe.output.quantity);
        }

        if (recipe.tools.size() > 0) {
            printer.text("tools: ");
            needsDelimiter = true;
            for (ItemModel tool : recipe.tools) {
                if (needsDelimiter) printer.text(", ");
                needsDelimiter = true;
                printer.text(tool.displayName);
            }
            printer.line();
        }

        printer.outdent();
    }
}
