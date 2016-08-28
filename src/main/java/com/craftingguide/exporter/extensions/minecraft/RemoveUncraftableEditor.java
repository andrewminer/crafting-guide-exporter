package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.baseWorkers.ItemRemoverEditor;
import java.util.ArrayList;

public class RemoveUncraftableEditor extends ItemRemoverEditor {

    public RemoveUncraftableEditor() {
        super(RemoveUncraftableEditor.PATTERNS);
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private static ArrayList<String> PATTERNS = null;

    static {
        PATTERNS = new ArrayList<String>();
        PATTERNS.add(".*Damaged Anvil$");
        PATTERNS.add(".*Monster Egg$");
        PATTERNS.add("^Spawn.*");
        PATTERNS.add("^tile.*");
        PATTERNS.add("minecraft:flowing_lava");
        PATTERNS.add("minecraft:lit_furnace");
        PATTERNS.add("minecraft:portal");
        PATTERNS.add("minecraft:flowing_water");
    }
}
