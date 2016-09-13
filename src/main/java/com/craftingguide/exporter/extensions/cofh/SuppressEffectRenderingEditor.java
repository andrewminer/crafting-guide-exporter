package com.craftingguide.exporter.extensions.cofh;

import com.craftingguide.exporter.Editor;
import com.craftingguide.exporter.models.ModPackModel;

public class SuppressEffectRenderingEditor extends Editor {

    // Editor Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    public void edit(ModPackModel modPack) {
        modPack.getItem("ThermalExpansion:meter:1").setEffectRenderable(false);
    }
}
