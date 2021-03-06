package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.Gatherer;
import com.craftingguide.exporter.models.ItemModel;
import com.craftingguide.exporter.models.ItemStackModel;
import com.craftingguide.exporter.models.ModPackModel;
import com.craftingguide.exporter.models.RecipeModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.StatCollector;

public class PotionRecipeGatherer extends Gatherer {

    // IGatherer Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void gather(ModPackModel modPack) {
        this.modPack = modPack;
        this.brewingStand = modPack.getItem(BREWING_STAND_ID);
        this.basePotion = modPack.getItem(BASE_POTION_ID);

        this.removeExistingPotions();
        this.gatherPotionIngredients();

        this.knownPotions.put(this.basePotion.getId(), this.basePotion);
        this.potionsToExplore.push(this.basePotion);
        Set<RecipeModel> foundRecipes = new HashSet<>();

        while (!this.potionsToExplore.isEmpty()) {
            ItemModel potion = this.potionsToExplore.removeFirst();
            this.exploredPotions.put(potion.getDisplayName(), potion);

            for (RecipeModel recipe : this.explorePotion(potion)) {
                ItemModel newPotion = recipe.getOutput().getItem();

                if (this.exploredPotions.containsKey(newPotion.getDisplayName())) continue;

                foundRecipes.add(recipe);
                this.potionsToExplore.add(newPotion);
            }
        }

        for (ItemModel item : this.knownPotions.values()) {
            modPack.addItem(item);
        }

        for (RecipeModel recipe : foundRecipes) {
            modPack.addRecipe(recipe);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    // Allowing Mundane, Potent, and Thick Potions as source potions leads to all sorts of silly crafting plans
    private static Set<Integer> FORBIDDEN_INPUT_POTIONS = new HashSet<>(Arrays.asList(32, 48, 8192));

    private static String POTION_NAME = "minecraft:potion"; // must be first

    private static String BREWING_STAND_ID = "minecraft:brewing_stand";

    private static Integer BASE_MODIFIER = 0;

    private static String BASE_POTION_ID = POTION_NAME + ":" + BASE_MODIFIER;

    private static String[] ROMAN_NUMERAL = { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" };

    private static int SEC_PER_MIN = 60;

    private static int TICKS_PER_SEC = 20;

    // Private Methods /////////////////////////////////////////////////////////////////////////////////////////////////

    private void assignDisplayName(ItemModel potion) {
        List<PotionEffect> effects = potion.getEffectsAsPotion();

        if (effects == null) return;
        if (effects.size() == 0) return;

        if (effects.size() > 1) {
            System.err.print(potion.getId() + " has multiple effects! Using only the first in the name...");
        }

        PotionEffect effect = effects.get(0);
        StringBuffer buffer = new StringBuffer();

        if (effect.getIsAmbient()) {
            buffer.append("Ambient ");
        }

        if (ItemPotion.isSplash(potion.getRawItemStack().getItemDamage())) {
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
            buffer.append(String.format(" (%1$d:%2$02d)", minutes, seconds));
        }

        potion.setDisplayName(buffer.toString());
    }

    private Set<RecipeModel> explorePotion(ItemModel potion) {
        Set<RecipeModel> results = new HashSet<RecipeModel>();

        if (FORBIDDEN_INPUT_POTIONS.contains(potion.getType())) return results;

        for (ItemModel ingredient : this.ingredients) {
            Integer newType = PotionHelper.applyIngredient(potion.getType(), ingredient.getPotionEffect());
            if (newType == 0) continue;
            if (newType == potion.getType()) continue;

            ItemModel newPotion = this.getPotionModel(newType);
            results.add(this.createRecipe(potion, ingredient, newPotion));
        }
        return results;
    }

    private void gatherPotionIngredients() {
        for (ItemModel item : this.modPack.getAllItems()) {
            if (!item.isPotionIngredient()) continue;
            this.ingredients.add(item);
        }
    }

    private ItemModel getPotionModel(Integer type) {
        ItemStack newPotionStack = new ItemStack(this.basePotion.getRawItemStack().getItem(), 1, type);

        String id = POTION_NAME + ":" + type;
        ItemModel potion = new ItemModel(id, newPotionStack);
        potion.setEffectRenderable(false);
        this.assignDisplayName(potion);

        if (this.knownPotions.containsKey(potion.getDisplayName())) {
            potion = this.knownPotions.get(potion.getDisplayName());
        } else {
            this.knownPotions.put(potion.getDisplayName(), potion);
        }

        return potion;
    }

    private RecipeModel createRecipe(ItemModel input, ItemModel ingredient, ItemModel output) {
        ItemStackModel ingredientStack = new ItemStackModel(ingredient, 1);
        ItemStackModel inputStack = new ItemStackModel(input, 1);
        ItemStackModel outputStack = new ItemStackModel(output, 3);

        RecipeModel recipe = new RecipeModel(outputStack);
        recipe.setInputAt(0, 1, ingredientStack);
        recipe.setInputAt(2, 0, inputStack);
        recipe.setInputAt(2, 1, inputStack);
        recipe.setInputAt(2, 2, inputStack);
        recipe.addTool(this.brewingStand);

        return recipe;
    }

    private void removeExistingPotions() {
        for (ItemModel item : this.modPack.getAllItems()) {
            if (!item.isPotion()) continue;
            this.modPack.removeItem(item);
        }
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ItemModel basePotion = null;

    private ItemModel brewingStand = null;

    private List<ItemModel> ingredients = new ArrayList<>();

    private ModPackModel modPack = null;

    private Map<String, ItemModel> knownPotions = new HashMap<>();

    private Map<String, ItemModel> exploredPotions = new HashMap<String, ItemModel>();

    private LinkedList<ItemModel> potionsToExplore = new LinkedList<>();
}
