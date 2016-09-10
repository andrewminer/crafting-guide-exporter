package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fluids.FluidStack;

public class ItemModel implements Comparable<ItemModel> {

    public ItemModel(String id, ItemStack rawItemStack) {
        this(id, rawItemStack.getDisplayName());
        this.setRawItemStack(rawItemStack);
    }

    public ItemModel(String id, FluidStack rawFluidStack) {
        this(id, rawFluidStack.getFluid().getLocalizedName(rawFluidStack));
        this.setRawFluidStack(rawFluidStack);
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
        if (!(this.getRawItemStack().getItem() instanceof ItemPotion)) return new ArrayList<PotionEffect>();
        ItemPotion rawPotion = (ItemPotion) this.getRawItemStack().getItem();
        return rawPotion.getEffects(this.getRawItemStack());
    }

    public int getType() {
        return this.rawItemStack.getItemDamage();
    }

    public boolean isPotion() {
        return this.getRawItemStack().getItem() instanceof ItemPotion;
    }

    public String getPotionEffect() {
        return this.getRawItemStack().getItem().getPotionEffect(this.getRawItemStack());
    }

    public boolean isPotionIngredient() {
        return this.getRawItemStack().getItem().isPotionIngredient(this.getRawItemStack());
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

    public boolean isEffectRenderable() {
        return this.effectRenderable;
    }

    public void setEffectRenderable(boolean effectRenderable) {
        this.effectRenderable = effectRenderable;
    }

    public boolean isFluid() {
        return this.rawFluidStack != null;
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

    public SortedSet<RecipeModel> getRecipes() {
        return Collections.unmodifiableSortedSet(this.recipes);
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

    public FluidStack getRawFluidStack() {
        return this.rawFluidStack;
    }

    public void setRawFluidStack(FluidStack rawFluidStack) {
        this.rawFluidStack = rawFluidStack;
    }

    public ItemStack getRawItemStack() {
        return this.rawItemStack;
    }

    private void setRawItemStack(ItemStack rawItemStack) {
        this.rawItemStack = rawItemStack;
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

    private boolean effectRenderable = true;

    private boolean gatherable = false;

    private String groupName = "Other";

    private SortedSet<RecipeModel> recipes = new TreeSet<RecipeModel>();;

    private FluidStack rawFluidStack = null;

    private ItemStack rawItemStack = null;
}
