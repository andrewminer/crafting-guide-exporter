package com.craftingguide.exporter.models;

import cpw.mods.fml.common.ModContainer;
import java.util.Comparator;

public class ModModel {

    public ModModel(ModContainer rawMod) {
        this(rawMod.getModId(), rawMod.getName(), rawMod.getVersion());
        this.rawMod = rawMod;
    }

    public ModModel(String id, String displayName, String version) {
        this.id = id;
        this.displayName = displayName;
        this.version = version;
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ModModel> SORT_BY_DISPLAY_NAME = new Comparator<ModModel>() {

        @Override
        public int compare(ModModel a, ModModel b) {
            return a.displayName.compareTo(b.displayName);
        }
    };

    // Public Properties ///////////////////////////////////////////////////////////////////////////////////////////////

    public String id = null;

    public String displayName = null;

    public ModContainer rawMod = null;

    public String version = null;

}
