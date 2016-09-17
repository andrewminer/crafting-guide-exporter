package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.baseWorkers.ItemRemoverEditor;
import java.util.Arrays;

public class AgriCraftItemRemoverEditor extends ItemRemoverEditor {

    public AgriCraftItemRemoverEditor() {
        super(Arrays.asList("AgriCraft:crop[^s].*"));
    }
}
