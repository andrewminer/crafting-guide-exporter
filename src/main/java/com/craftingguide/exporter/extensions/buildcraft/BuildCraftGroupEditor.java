package com.craftingguide.exporter.extensions.buildcraft;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class BuildCraftGroupEditor extends GroupAssignmentEditor {

    public BuildCraftGroupEditor() {
        super("BuildCraft");

        // Rough categorization by single words
        this.addPattern("Electronics (gates)", ".*Gate.*");
        this.addPattern("Electronics (parts)", ".*Chipset|.*Wire");
        this.addPattern("Filters & Lenses", "Filter.*|Lens.*");
        this.addPattern("Fluids", "Fuel.*|Oil.*|Water.*");
        this.addPattern("Machine Parts", ".*Gear|.*Mark");
        this.addPattern("Machines (building)", ".*Library|.*Packager|.*Well|.*Workbench|Zone.*");
        this.addPattern("Machines (electronics)", ".*Table");
        this.addPattern("Machines (power)", ".*Engine");
        this.addPattern("Machines (transport)", ".*Buffer");
        this.addPattern("Pipes (transport)", ".*Transport Pipe");
        this.addPattern("Pipes (fluid)", ".*Fluid Pipe");
        this.addPattern("Pipes (energy)", ".*Kinesis Pipe");
        this.addPattern("Pipes (structure)", "Facade.*|.*Structure.*");
        this.addPattern("Robotics", ".*Board.*|.*Station|.*Robot.*");

        // Detailed categorization by full names
        this.addPattern("Documents", "Blueprint|List|Map Location|Template");
        this.addPattern("Fluids", "Tank");
        this.addPattern("Machine Parts", "Package");
        this.addPattern("Machines (building)", "Architect Table|Builder|Filler|Quarry");
        this.addPattern("Machines (electronics)", "Laser");
        this.addPattern("Machines (fluid)", "Flood Gate|Pump|Refinery");
        this.addPattern("Machines (transport)", "Chute");
        this.addPattern("Pipes (fluid)", "Pipe Sealant");
        this.addPattern("Pipes (energy)", "Power Adapter");
        this.addPattern("Pipes (structure)", "Frame|Mining Pipe|Pipe Plug");
        this.addPattern("Robotics", "Redstone Crystal");
        this.addPattern("Tools", "Debugger|Paintbrush|Gate Copier|Wrench");
    }
}
