package com.craftingguide.exporter.extensions.bigreactors;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class BigReactorsGroupEditor extends GroupAssignmentEditor {

    public BigReactorsGroupEditor() {
        super("BigReactors");

        this.addPattern("Fluids", ".*Fluid.*");
        this.addPattern("Minerals", ".*Ingot|.*Block|.*Dust|.*Bar|.*Ore");
        this.addPattern("Tools", ".*Diagnostic.*|.*Reprocesssor");
        this.addPattern("Reactor Parts", "Reactor.*|.*Fuel Rod");
        this.addPattern("Turbine Parts", "Turbine.*");
    }
}
