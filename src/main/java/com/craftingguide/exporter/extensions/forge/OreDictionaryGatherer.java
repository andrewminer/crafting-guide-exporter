package com.craftingguide.exporter.extensions.forge;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryGatherer extends Gatherer {

    @Override
    public void gather(ModPackModel modPack) {
        for (String oreName : OreDictionary.getOreNames()) {
            for (ItemStack itemStack : OreDictionary.getOres(oreName)) {
                ItemModel itemModel = modPack.getItem(itemStack);
                if (itemModel != null) {
                    modPack.addOreEntry(oreName, itemModel);
                }
            }
        }
    }
}
