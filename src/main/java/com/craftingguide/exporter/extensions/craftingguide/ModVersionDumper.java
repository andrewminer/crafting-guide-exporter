package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import com.craftingguide.util.Printer;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModVersionDumper extends AbstractCraftingGuideDumper {

    // IDumper Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    public void dump(ModPackModel modPack) {
        for (ModModel mod : modPack.getAllMods()) {
            this.printMod(mod);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private Map<String, List<ItemModel>> groupItems(Collection<ItemModel> items) {
        Map<String, List<ItemModel>> result = new HashMap<>();
        for (ItemModel item : items) {
            List<ItemModel> groupItems = result.get(item.getGroupName());
            if (groupItems == null) {
                groupItems = new ArrayList<ItemModel>();
                result.put(item.getGroupName(), groupItems);
            }
            groupItems.add(item);
        }
        return result;
    }

    private void printGroup(String groupName, List<ItemModel> items, Printer printer) throws IOException {
        if (groupName.length() == 0) {
            groupName = "Other";
        }

        printer.println("group: " + groupName);
        printer.println();
        printer.indent();

        for (ItemModel item : items) {
            this.printItem(item, printer);
        }

        printer.outdent();
    }

    private void printItem(ItemModel item, Printer printer) throws IOException {
        printer.println("item: " + item.getDisplayName());
        printer.indent();

        if (item.isGatherable()) {
            printer.println("gatherable: yes");
        }

        if (item.getRecipes().size() > 0) {
            for (RecipeModel recipe : item.getRecipes()) {
                this.printRecipe(recipe, printer);
            }
        }

        printer.outdent();
        printer.println();
    }

    private void printMod(ModModel mod) {
        if (!mod.isEnabled()) return;
        if (!this.getFileManager().ensureDir(this.getFileManager().getModVersionDir(mod))) return;

        String modVersionFile = this.getFileManager().getModVersionFile(mod);
        FileWriter fileWriter = null;
        Printer printer = null;

        try {
            fileWriter = new FileWriter(modVersionFile, false);
            printer = new Printer(fileWriter);

            printer.println("schema: 1");
            printer.println();

            Map<String, List<ItemModel>> itemsByGroup = this.groupItems(mod.getAllItems());
            List<String> groups = new ArrayList<String>(itemsByGroup.keySet());
            groups.sort((a, b)-> a.compareTo(b));

            for (String groupName : groups) {
                this.printGroup(groupName, itemsByGroup.get(groupName), printer);
            }
        } catch (IOException e) {
            logger.error("Could not write to " + modVersionFile + ": ");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
            } catch (Throwable e) {}
        }
    }

    private void printRecipe(RecipeModel recipe, Printer printer) throws IOException {
        boolean needsDelimiter = false;

        printer.println("recipe:");
        printer.indent();

        if (recipe.getExtras().size() > 0) {
            needsDelimiter = false;
            printer.print("extras: ");
            for (ItemStackModel extraStack : recipe.getExtras()) {
                if (needsDelimiter) printer.print(", ");
                needsDelimiter = true;
                this.printStack(extraStack, printer);
            }
            printer.println();
        }

        printer.print("input: ");
        needsDelimiter = false;
        for (ItemStackModel inputStack : recipe.getInputs()) {
            if (needsDelimiter) printer.print(", ");
            needsDelimiter = true;
            this.printStack(inputStack, printer);
        }
        printer.println();

        printer.println("pattern: " + recipe.getPattern());

        if (recipe.getOutput().getQuantity() > 1) {
            printer.println("quantity: " + recipe.getOutput().getQuantity());
        }

        if (recipe.getTools().size() > 0) {
            printer.print("tools: ");
            needsDelimiter = false;
            for (ItemModel tool : recipe.getTools()) {
                if (needsDelimiter) printer.print(", ");
                needsDelimiter = true;
                printer.print(tool.getDisplayName());
            }
            printer.println();
        }

        printer.outdent();
    }

    private void printStack(ItemStackModel stack, Printer printer) throws IOException {
        if (stack.getQuantity() > 1) {
            printer.print(stack.getQuantity() + " ");
        }
        printer.print(stack.getItem().getDisplayName());
    }
}
