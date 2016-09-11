package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.baseWorkers.GatherableEditor;

public class MinecraftGatherableEditor extends GatherableEditor {

    public MinecraftGatherableEditor() {
        this.addPattern("Coal");
        this.addPattern("Diamond");
        this.addPattern("Emerald");
        this.addPattern("Flint");
        this.addPattern("Glowstone Dust");
        this.addPattern("Gravel");
        this.addPattern("Lapis Lazuli");
        this.addPattern("Lava");
        this.addPattern("Magma Cream");
        this.addPattern("Nether Brick (block)");
        this.addPattern("Nether Quartz");
        this.addPattern("Redstone");
        this.addPattern("Sand");
        this.addPattern("String");
        this.addPattern("Wool");
        this.addPattern("Water");
    }
}
