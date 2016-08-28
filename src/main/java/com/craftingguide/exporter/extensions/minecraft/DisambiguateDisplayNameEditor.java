package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.baseWorkers.ItemRenamerEditor;
import java.util.HashMap;

public class DisambiguateDisplayNameEditor extends ItemRenamerEditor {

    public DisambiguateDisplayNameEditor() {
        super(DisambiguateDisplayNameEditor.MAPPINGS);
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static HashMap<String, String> MAPPINGS = null;

    static {
        MAPPINGS = new HashMap<>();
        MAPPINGS.put("minecraft:stone_button", "Button (stone)");
        MAPPINGS.put("minecraft:wooden_button", "Button (wooden)");
        MAPPINGS.put("minecraft:clay_ball", "Clay (item)");
        MAPPINGS.put("minecraft:clay", "Clay (block)");
        MAPPINGS.put("minecraft:golden_apple:1", "Golden Apple (magical)");
        MAPPINGS.put("minecraft:melon_block", "Melon (block)");
        MAPPINGS.put("minecraft:melon", "Melon (item)");
        MAPPINGS.put("minecraft:brown_mushroom", "Mushroom (brown)");
        MAPPINGS.put("minecraft:red_mushroom", "Mushroom (red)");
        MAPPINGS.put("minecraft:red_mushroom_block", "Mushroom (red block)");
        MAPPINGS.put("minecraft:brown_mushroom_block", "Mushroom (brown block)");
        MAPPINGS.put("minecraft:record_13", "Music Disc (13)");
        MAPPINGS.put("minecraft:record_ward", "Music Disc (ward)");
        MAPPINGS.put("minecraft:record_stal", "Music Disc (stal)");
        MAPPINGS.put("minecraft:record_mellohi", "Music Disc (mellohi)");
        MAPPINGS.put("minecraft:record_blocks", "Music Disc (blocks)");
        MAPPINGS.put("minecraft:record_11", "Music Disc (11)");
        MAPPINGS.put("minecraft:record_cat", "Music Disc (cat)");
        MAPPINGS.put("minecraft:record_far", "Music Disc (far)");
        MAPPINGS.put("minecraft:record_strad", "Music Disc (strad)");
        MAPPINGS.put("minecraft:record_chirp", "Music Disc (chirp)");
        MAPPINGS.put("minecraft:nether_brick", "Nether Brick (block)");
        MAPPINGS.put("minecraft:netherbrick", "Nether Brick (item)");
        MAPPINGS.put("minecraft:stone_pressure_plate", "Pressure Plate (stone)");
        MAPPINGS.put("minecraft:wooden_pressure_plate", "Pressure Plate (wooden)");
        MAPPINGS.put("minecraft:snow_layer:0", "Snow (layer)");
        MAPPINGS.put("minecraft:snow", "Snow (block)");
    }
}
