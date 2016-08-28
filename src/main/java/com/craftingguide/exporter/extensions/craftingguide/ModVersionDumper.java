package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import com.craftingguide.util.Printer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ModVersionDumper implements IDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        Map<String, List<ItemModel>> itemsByMod = modPack.getItemsByMod();
        for (String modId : itemsByMod.keySet()) {
            ModModel mod = modPack.getMod(modId);
            this.printMod(mod, itemsByMod.get(modId));
        }
    }

    // Private Class Methods ///////////////////////////////////////////////////////////////////////////////////////////

    private static String slugify(String text) {
        if (text == null) return null;

        String result = text.toLowerCase();
        result = result.replaceAll("[^-a-zA-Z0-9._]", "_");
        result = result.replaceAll("__+", "_");
        result = result.replaceAll("^_", "");
        result = result.replaceAll("_$", "");

        return result;
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static String DUMP_DIR = "./dumps/crafting-guide/%1$s/versions/%2$s";

    private static String DUMP_FILE = "mod-version.cg";

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

    private void printMod(ModModel mod, List<ItemModel> items) {
        if (items.size() == 0) return;

        String modSlug = slugify(mod.displayName);
        String versionSlug = slugify(mod.version);
        String versionDir = String.format(DUMP_DIR, modSlug, versionSlug);

        File versionDirFile = new File(versionDir);
        if (!versionDirFile.exists() && !versionDirFile.mkdirs()) {
            System.err.println("Could not create dump dir: " + versionDir);
            return;
        }

        File outputFile = new File(versionDir, DUMP_FILE);
        FileWriter fileWriter = null;
        Printer printer = null;

        System.out.println("Writing to: " + versionDir + "/" + DUMP_FILE);
        try {
            fileWriter = new FileWriter(outputFile, false);
            printer = new Printer(fileWriter);

            printer.line("schema: 1");
            printer.line();

            for (ItemModel item : items) {
                this.printItem(item, printer);
            }
        } catch (IOException e) {
            System.err.println("Could not write to " + outputFile + ": ");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    private void printRecipe(RecipeModel recipe, Printer printer) throws IOException {
        boolean needsDelimiter = false;

        printer.line("recipe:");
        printer.indent();

        if (recipe.extras.size() > 0) {
            needsDelimiter = false;
            printer.text("extras: ");
            for (ItemStackModel extraStack : recipe.extras) {
                if (needsDelimiter) printer.text(", ");
                needsDelimiter = true;
                this.printStack(extraStack, printer);
            }
            printer.line();
        }

        printer.text("inputs: ");
        needsDelimiter = false;
        for (ItemStackModel inputStack : recipe.inputs) {
            if (needsDelimiter) printer.text(", ");
            needsDelimiter = true;
            this.printStack(inputStack, printer);
        }
        printer.line();

        printer.line("pattern: " + recipe.getPattern());

        if (recipe.output.quantity > 1) {
            printer.line("quantity: " + recipe.output.quantity);
        }

        if (recipe.tools.size() > 0) {
            printer.text("tools: ");
            needsDelimiter = false;
            for (ItemModel tool : recipe.tools) {
                if (needsDelimiter) printer.text(", ");
                needsDelimiter = true;
                printer.text(tool.displayName);
            }
            printer.line();
        }

        printer.outdent();
    }

    private void printStack(ItemStackModel stack, Printer printer) throws IOException {
        if (stack.quantity > 1) {
            printer.text(stack.quantity + " ");
        }
        printer.text(stack.item.displayName);

    }
}
