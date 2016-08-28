package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

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
        return maxCol - minCol;
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
        return maxRow - minRow;
    }

    public String getPattern() {
        StringBuffer buffer = new StringBuffer(".........");

        for (int row = 0; row < this.inputGrid.length; row++) {
            for (int col = 0; col < this.inputGrid[row].length; col++) {
                if (this.inputGrid[row][col] == null) continue;

                char index = ("" + this.inputs.indexOf(this.inputGrid[row][col])).charAt(0);
                buffer.setCharAt(row * 3 + col, index);
            }
        }
        buffer.insert(3, ' ');
        buffer.insert(7, ' ');
        return buffer.toString();
    }

    public boolean isCraftingTableRequired() {
        return this.getWidth() > 2 || this.getHeight() > 2;
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer("[");

        boolean needsDelimiter = false;
        for (ItemStackModel input : this.inputs) {
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

    public List<ItemStackModel> extras = new ArrayList<ItemStackModel>();;

    public List<ItemStackModel> inputs = new ArrayList<ItemStackModel>();;

    public ItemStackModel output = null;

    public ItemStackModel[][] inputGrid = { { null, null, null }, { null, null, null }, { null, null, null } };

    public List<ItemModel> tools = new ArrayList<ItemModel>();;
}
