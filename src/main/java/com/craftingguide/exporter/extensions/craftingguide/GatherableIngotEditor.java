package com.craftingguide.exporter.extensions.craftingguide;

import com.craftingguide.exporter.baseWorkers.GatherableEditor;

public class GatherableIngotEditor extends GatherableEditor {

    public GatherableIngotEditor() {
        this.addPattern("Copper Ingot");
        this.addPattern("Ferrous Ingot");
        this.addPattern("Gold Ingot");
        this.addPattern("Iron Ingot");
        this.addPattern("Lead Ingot");
        this.addPattern("Shiny Ingot");
        this.addPattern("Silver Ingot");
        this.addPattern("Tin Ingot");
    }
}
