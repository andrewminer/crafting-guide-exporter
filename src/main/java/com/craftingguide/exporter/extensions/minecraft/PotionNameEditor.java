package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.IEditor;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ModPackModel;
import java.util.List;
import net.minecraft.item.ItemPotion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

public class PotionNameEditor implements IEditor {

    // IEditor Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void edit(ModPackModel modPack) {
        for (ItemModel item : modPack.getAllItems()) {
            if (!(item.rawStack.getItem() instanceof ItemPotion)) continue;

            ItemPotion potionItem = (ItemPotion) item.rawStack.getItem();
            List<PotionEffect> effects = potionItem.getEffects(item.rawStack);

            if (effects == null) continue;
            if (effects.size() == 0) continue;

            if (effects.size() > 1) {
                System.err.print(item.id + " has multiple effects! Using only the first in the name...");
            }

            PotionEffect effect = effects.get(0);
            StringBuffer buffer = new StringBuffer();

            if (effect.getIsAmbient()) {
                buffer.append("Ambient ");
            }

            if (ItemPotion.isSplash(item.rawStack.getItemDamage())) {
                buffer.append("Splash ");
            }

            buffer.append("Potion of ");
            buffer.append(StatCollector.translateToLocal(effect.getEffectName()));

            if (effect.getAmplifier() > 0) {
                buffer.append(" ");

                int effectiveAmplifier = effect.getAmplifier() + 1;
                if (effectiveAmplifier < ROMAN_NUMERAL.length) {
                    buffer.append(ROMAN_NUMERAL[effectiveAmplifier]);
                } else {
                    buffer.append("" + effectiveAmplifier);
                }
            }

            int durationInSec = (int) (effect.getDuration() / TICKS_PER_SEC);
            if (durationInSec > 0) {
                int minutes = (int) (durationInSec / SEC_PER_MIN);
                int seconds = (int) (durationInSec % SEC_PER_MIN);
                buffer.append(String.format(" (%1$02d:%2$02d)", minutes, seconds));
            }

            item.displayName = buffer.toString();
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private static String[] ROMAN_NUMERAL = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    private static int TICKS_PER_SEC = 20;

    private static int SEC_PER_MIN = 60;
}
