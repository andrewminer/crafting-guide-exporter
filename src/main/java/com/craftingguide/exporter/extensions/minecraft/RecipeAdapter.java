package com.craftingguide.exporter.extensions.minecraft;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class RecipeAdapter {

    public RecipeAdapter(IRecipe recipe) {
        this._recipe = recipe;
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public int getHeight() {
        if (this._recipe instanceof ShapedRecipes) {
            return ((ShapedRecipes) this._recipe).recipeHeight;
        } else if (this._recipe instanceof ShapedOreRecipe) {
            try {
                Field field = ShapedOreRecipe.class.getDeclaredField("height");
                field.setAccessible(true);
                return ((Number) field.get(this._recipe)).intValue();
            } catch (Throwable e) {
                System.err.println("Could not get recipe height by reflection");
                e.printStackTrace();
            }
        }

        return -1;
    }

    public List<ItemStack> getInputs() {
        if (!this.isSupported()) return null;

        ArrayList<ItemStack> results = new ArrayList<ItemStack>();

        if (this._recipe instanceof ShapelessRecipes) {
            for (ItemStack itemStack : (List<ItemStack>) ((ShapelessRecipes) this._recipe).recipeItems) {
                results.add(itemStack);
            }
        } else if (this._recipe instanceof ShapedRecipes) {
            for (ItemStack itemStack : ((ShapedRecipes) this._recipe).recipeItems) {
                results.add(itemStack);
            }
        } else if (this.isOreDict()) {
            List<Object> inputs = null;

            if (this._recipe instanceof ShapelessOreRecipe) {
                inputs = ((ShapelessOreRecipe) this._recipe).getInput();
            } else if (this._recipe instanceof ShapedOreRecipe) {
                inputs = (List<Object>) Arrays.asList(((ShapedOreRecipe) this._recipe).getInput());
            }

            for (Object inputObj : inputs) {
                if (inputObj instanceof ItemStack) {
                    results.add((ItemStack) inputObj);
                } else if (inputObj instanceof List<?>) {
                    results.add(((List<ItemStack>) inputObj).get(0));
                } else if (inputObj == null) {
                    results.add(null);
                } else {
                    System.err.println("Unexpected recipe ingredient");
                }
            }
        }

        return results;
    }

    public int getWidth() {
        if (this._recipe instanceof ShapedRecipes) {
            return ((ShapedRecipes) this._recipe).recipeWidth;
        } else if (this._recipe instanceof ShapedOreRecipe) {
            try {
                Field field = ShapedOreRecipe.class.getDeclaredField("width");
                field.setAccessible(true);
                return ((Number) field.get(this._recipe)).intValue();
            } catch (Throwable e) {
                System.err.println("Could not get recipe width by reflection");
                e.printStackTrace();
            } // foo
        }

        return -1;
    }

    public ItemStack getOutput() {
        return this._recipe.getRecipeOutput(); // foo
    }

    public boolean isOreDict() {
        if (this._recipe instanceof ShapedOreRecipe) return true;
        if (this._recipe instanceof ShapelessOreRecipe) return true;
        return false;
    }

    public boolean isShaped() {
        if (this._recipe instanceof ShapedRecipes) return true;
        if (this._recipe instanceof ShapedOreRecipe) return true;
        return false;
    }

    public boolean isShapeless() {
        if (this._recipe instanceof ShapelessRecipes) return true;
        if (this._recipe instanceof ShapelessOreRecipe) return true;
        return false;
    }

    public boolean isSupported() {
        return this.isShaped() || this.isShapeless();
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private IRecipe _recipe = null;
}
