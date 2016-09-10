package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class ThermalFoundationGroupEditor extends GroupAssignmentEditor {

    public ThermalFoundationGroupEditor() {
        super("ThermalFoundation");

        this.addPattern("Armor", ".*Boots|.*Chestplate|.*Helm(et)?|.*Leggings");
        this.addPattern("Mob Drops", "Aerotheum.*|Basalz.*|Blitz.*|Blizz.*");
        this.addPattern("Fluids", "Blazing.*|Destabilized.*|Energized.*|Gelid.*|Liquifacted.*|Primal.*|Resonant.*");
        this.addPattern("Fluids", "Steam|Tectonic.*|Zephyrean.*");
        this.addPattern("Fluids", ".*Bucket");
        this.addPattern("Gears", ".*Gear");
        this.addPattern("Minerals", "Cinnabar|.*Blend|.*Block|.*Dust|.*Ingot|Niter|.*Nugget|.*Ore|Pulverized.*|Sulfur");
        this.addPattern("Tools", ".*Axe|.*Fishing Rod|.*Hoe|.*Lexicon|.*Pickaxe|.*Shears|.*Shovel|.*Sickle");
        this.addPattern("Weapons", ".*Bow|.*Sword");
    }
}
