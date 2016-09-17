package com.craftingguide.exporter.extensions.solarexpansion;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class SolarExpansionGroupEditor extends GroupAssignmentEditor {

    public SolarExpansionGroupEditor() {
        super("SolarExpansion");

        // Low-Priority
        this.addPattern("Parts", ".*Cell|.*Solar Core");
        this.addPattern("Helmets", ".*Helmet");
        this.addPattern("Minerals", ".*Ingot|.*Nugget|.*Shard|");
        this.addPattern("Solar Panels", ".*Solar Panel");
        this.addPattern("Ducts (transport)", ".*Viaduct.*");
    }
}
