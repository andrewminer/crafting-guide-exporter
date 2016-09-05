package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

public class ItemModel implements Comparable<ItemModel> {

    public ItemModel(String id, ItemStack rawStack) {
        this(id, rawStack.getDisplayName());
        this.setRawStack(rawStack);
    }

    public ItemModel(String id, String displayName) {
        this.setId(id);
        this.setDisplayName(displayName);
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ItemModel> SORT_BY_DISPLAY_NAME = new Comparator<ItemModel>() {

        @Override
        public int compare(ItemModel a, ItemModel b) {
            return a.displayName.compareTo(b.displayName);
        }
    };

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public List<PotionEffect> getEffectsAsPotion() {
        if (!(this.getRawStack().getItem() instanceof ItemPotion)) return new ArrayList<PotionEffect>();
        ItemPotion rawPotion = (ItemPotion) this.getRawStack().getItem();
        return rawPotion.getEffects(this.getRawStack());
    }

    public int getType() {
        return this.rawStack.getItemDamage();
    }

    public boolean isFromMod(String modId) {
        int index = this.getId().indexOf(':');
        if (index == -1) return false;

        String myModPrefix = this.getId().substring(0, index);
        return myModPrefix.equals(modId);
    }

    public boolean isPotion() {
        return this.getRawStack().getItem() instanceof ItemPotion;
    }

    public String getPotionEffect() {
        return this.getRawStack().getItem().getPotionEffect(this.getRawStack());
    }

    public boolean isPotionIngredient() {
        return this.getRawStack().getItem().isPotionIngredient(this.getRawStack());
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getId() {
        return this.id;
    }

    private void setId(String id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        this.id = id;
    }

    public String getDisplayName() {
        if (this.displayName == null) return this.id;
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isGatherable() {
        if (this.getRecipes().isEmpty()) return true;
        return this.gatherable;
    }

    public void setGatherable(boolean gatherable) {
        this.gatherable = gatherable;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<RecipeModel> getRecipes() {
        return Collections.unmodifiableList(this.recipes);
    }

    public void addRecipe(RecipeModel recipe) {
        if (recipe.getOutput().getItem() != this) {
            throw new IllegalArgumentException("recipe does not produce this item");
        }
        this.recipes.add(recipe);
    }

    public void removeRecipe(RecipeModel recipe) {
        this.recipes.remove(recipe);
    }

    public ItemStack getRawStack() {
        return this.rawStack;
    }

    private void setRawStack(ItemStack rawStack) {
        this.rawStack = rawStack;
    }

    // Comparable Overrides ////////////////////////////////////////////////////////////////////////////////////////////

    public int compareTo(ItemModel that) {
        if (!this.getDisplayName().equals(that.getDisplayName())) {
            return this.getDisplayName().compareTo(that.getDisplayName());
        }
        return this.getId().compareTo(that.getId());
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemModel)) return false;
        ItemModel that = (ItemModel) obj;

        if (!this.id.equals(that.id)) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.displayName + " <" + this.id + ">";
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String id = "";

    private String displayName = "";

    private boolean gatherable = false;

    private String groupName = "Other";

    private List<RecipeModel> recipes = new ArrayList<RecipeModel>();;

    private ItemStack rawStack = null;
}
