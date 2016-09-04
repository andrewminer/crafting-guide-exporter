package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.baseWorkers.GatherableEditor;

public class MinecraftGatherableEditor extends GatherableEditor {

    public MinecraftGatherableEditor() {
        this.addPattern("Coal");
        this.addPattern("Diamond");
        this.addPattern("Emerald");
        this.addPattern("Lapis Lazuli");
        this.addPattern("Magma Cream");
        this.addPattern("Nether Brick (block)");
        this.addPattern("Nether Quartz");
        this.addPattern("Redstone");
        this.addPattern("Wool");
    }
}
