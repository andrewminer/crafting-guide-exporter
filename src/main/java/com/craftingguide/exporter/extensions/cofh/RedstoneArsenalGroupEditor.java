package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class RedstoneArsenalGroupEditor extends GroupAssignmentEditor {

    public RedstoneArsenalGroupEditor() {
        super("RedstoneArsenal");

        // Low-Priority
        this.addPattern("Armor", ".*Armor.*|.*Boots|.*Chestplate|.*Helm|.*Leggings");
        this.addPattern("Minerals", ".*Crystal.*|.*Blend|.*Block|.*Ingot|.*Nugget|.*Rod");
        this.addPattern("Tools", ".*Axe|.*Wrench|.*Fishing Rod|.*Pickaxe|.*Shovel|.*Sickle");
        this.addPattern("Weapons", ".*BattleWrench|.*Bow|.*Sword");

        // High-Priority
        this.addPattern("Ducts (other)", "Luxduct|Structuralduct.*");
    }
}
