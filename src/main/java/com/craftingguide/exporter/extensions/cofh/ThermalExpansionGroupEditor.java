package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class ThermalExpansionGroupEditor extends GroupAssignmentEditor {

    public ThermalExpansionGroupEditor() {
        super("ThermalExpansion");

        // Low-Priority
        this.addPattern("Capacitors", ".*Capacitor");
        this.addPattern("Energy Creation & Storage Items", ".*Capacitor|.*Cell|.*Dynamo|.*Tesseract");
        this.addPattern("Fluid Items", ".*Florb.*|FluiVac|.*Tank");
        this.addPattern("Lights", ".*Illuminator|.*Lamp");
        this.addPattern("Machines", ".*Allocator|.*Apparatus|.*Accumulator.*|.*Activator|.*Assembler.*|.*Crucible.*|"
            + ".*Extruder.*|.*Furnace.*|.*Infuser.*|.*Insolator.*|.*Precipitator.*|.*Pulverizer.*|.*Sawmill.*|"
            + ".*Smasher.*|.*Smelter.*|.*Transposer.*|.*Workbench");
        this.addPattern("Minerals", ".*Phyto-Gro|.*Rockwool|.*Sawdust|.*Slag");
        this.addPattern("Parts", ".*Coil|.*Frame.*|.*Glass|.*Servo");
        this.addPattern("Plates", ".*Plate");
        this.addPattern("Storage Items", ".*Cache|.*Lock|.*Satchel|.*Strongbox");
        this.addPattern("Tools", ".*Chiller|Flux.*|.*Hammer|.*Sponge|.*Wrench");

        // High-Priority
        this.addPattern("Augments", "Augment.*");
        this.addPattern("Machines", "Nullifier");
        this.addPattern("Parts", "Redprint|Schematic");
        this.addPattern("Tools", "Debugger|FluiVac|Multimeter");
    }
}
