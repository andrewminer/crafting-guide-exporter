package com.craftingguide.exporter.extensions.agricraft;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class AgriCraftGroupEditor extends GroupAssignmentEditor {

    public AgriCraftGroupEditor() {
        super("AgriCraft");

        // Low-priority assignments
        this.addPattern("Exotic Seeds", ".*Seeds");

        // Rough categorization by single words
        this.addPattern("Crop Seeds", ".*Mushroom.*|Cactus.*|Carrot.*|Nitor.*|Potato.*|Sugarcane.*");
        this.addPattern("Fences", "Fence.*|Gate.*|.*Grate.*");
        this.addPattern("Flower Seeds", "Allium.*|.*Orchid.*|Daisy.*|Dandelion.*|.*Tulip.*|Poppy.*");
        this.addPattern("Farming Items", "Crop Sticks|.*Tank.*|.*Channel.*|Sprinkler|Water Pad");
        this.addPattern("Minerals", ".*Nugget|.*Shard");
        this.addPattern("Seed Management Items", "Clipping|.*Journal|.*Storage|.*Analyzer");
        this.addPattern("Tools", "Clipper|Debugger|.*Trowel|.*Rake|Magnifying.*");
    }
}
