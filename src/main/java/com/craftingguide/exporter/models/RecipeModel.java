package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class RecipeModel implements Comparable<RecipeModel> {

    public RecipeModel(ItemStackModel output) {
        this.setOutput(output);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean isCraftingTableRequired() {
        return this.getWidth() > 2 || this.getHeight() > 2;
    }

    public SortedSet<ItemStackModel> getExtras() {
        return Collections.unmodifiableSortedSet(this.extras);
    }

    public void addExtra(ItemStackModel stack) {
        if (stack == null) throw new IllegalArgumentException("stack cannot be null");
        this.extras.add(stack);
    }

    public int getHeight() {
        int minRow = this.inputGrid.length;
        int maxRow = 0;

        for (int row = 0; row < this.inputGrid.length; row++) {
            for (int col = 0; col < this.inputGrid[row].length; col++) {
                if (this.inputGrid[row][col] != null) {
                    minRow = Math.min(minRow, row);
                    maxRow = Math.max(maxRow, row);
                }
            }
        }
        if (maxRow < minRow) return 0;
        return maxRow - minRow + 1;
    }

    public ItemStackModel getInputAt(int row, int col) {
        return this.inputGrid[row][col];
    }

    public void setInputAt(int row, int col, ItemStackModel stack) {
        this.inputGrid[row][col] = stack;
        this.addInput(stack);
    }

    public SortedSet<ItemStackModel> getInputs() {
        return Collections.unmodifiableSortedSet(this.inputs);
    }

    public void addInput(ItemStackModel stack) {
        if (stack == null) throw new IllegalArgumentException("stack cannot be null");
        this.inputs.add(stack);
    }

    public ItemStackModel getOutput() {
        return this.output;
    }

    private void setOutput(ItemStackModel output) {
        if (output == null) throw new IllegalArgumentException("output cannot be null");
        this.output = output;
    }

    public String getPattern() {
        StringBuffer buffer = new StringBuffer(".........");

        List<ItemStackModel> inputs = new ArrayList<ItemStackModel>(this.getInputs());
        for (int row = 0; row < this.inputGrid.length; row++) {
            for (int col = 0; col < this.inputGrid[row].length; col++) {
                if (this.inputGrid[row][col] == null) continue;

                char index = ("" + inputs.indexOf(this.inputGrid[row][col])).charAt(0);
                buffer.setCharAt(row * 3 + col, index);
            }
        }
        buffer.insert(3, ' ');
        buffer.insert(7, ' ');
        return buffer.toString();
    }

    public int getQuantityRequired(String itemId) {
        int result = 0;

        for (int row = 0; row < this.inputGrid.length; row++) {
            for (int col = 0; col < this.inputGrid[row].length; col++) {
                ItemStackModel input = this.inputGrid[row][col];
                if (input == null) continue;
                if (!input.getItem().getId().equals(itemId)) continue;
                result += input.getQuantity();
            }
        }

        return result;
    }

    public SortedSet<ItemModel> getTools() {
        return Collections.unmodifiableSortedSet(this.tools);
    }

    public void addTool(ItemModel item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        this.tools.add(item);
    }

    public int getWidth() {
        int minCol = this.inputGrid.length;
        int maxCol = 0;

        for (int row = 0; row < this.inputGrid.length; row++) {
            for (int col = 0; col < this.inputGrid[row].length; col++) {
                if (this.inputGrid[row][col] != null) {
                    minCol = Math.min(minCol, col);
                    maxCol = Math.max(maxCol, col);
                }
            }
        }
        if (maxCol < minCol) return 0;
        return maxCol - minCol + 1;
    }

    // Comparable Overrides ////////////////////////////////////////////////////////////////////////////////////////////

    public int compareTo(RecipeModel that) {
        return this.toString().compareTo(that.toString());
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecipeModel)) return false;
        RecipeModel that = (RecipeModel) obj;
        return this.toString().equals(that.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");

        boolean needsDelimiter = false;
        for (ItemStackModel input : this.getInputs()) {
            if (needsDelimiter) buffer.append(", ");
            needsDelimiter = false;
            buffer.append(input.toString());
        }

        buffer.append("]<");
        buffer.append(this.getPattern());
        buffer.append("> ==> ");
        buffer.append(this.output.toString());

        return buffer.toString();
    }

    // Public Properties ///////////////////////////////////////////////////////////////////////////////////////////////

    private SortedSet<ItemStackModel> extras = new TreeSet<ItemStackModel>();;

    private SortedSet<ItemStackModel> inputs = new TreeSet<ItemStackModel>();;

    private ItemStackModel output = null;

    private ItemStackModel[][] inputGrid = { { null, null, null }, { null, null, null }, { null, null, null } };

    private SortedSet<ItemModel> tools = new TreeSet<ItemModel>();;
}
