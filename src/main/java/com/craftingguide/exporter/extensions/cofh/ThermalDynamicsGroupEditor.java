package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class ThermalDynamicsGroupEditor extends GroupAssignmentEditor {

    public ThermalDynamicsGroupEditor() {
        super("ThermalDynamics");

        // Low-Priority
        this.addPattern("Duct Adapters", ".*Cover|.*Filter|.*Relay|.*Retriever|.*Servo");
        this.addPattern("Ducts (energy)", ".*Fluxduct.*");
        this.addPattern("Ducts (fluid)", ".*Fluiduct.*");
        this.addPattern("Ducts (items)", ".*Itemduct.*");
        this.addPattern("Ducts (transport)", ".*Viaduct.*");

        // High-Priority
        this.addPattern("Ducts (other)", "Luxduct|Structuralduct.*");
    }
}
