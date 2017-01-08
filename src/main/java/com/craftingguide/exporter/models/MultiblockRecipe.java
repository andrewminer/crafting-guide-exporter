package com.craftingguide.exporter.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MultiblockRecipe {

    public MultiblockRecipe() {
        this.resize(3, 3, 3);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void resize(int x, int y, int z) {
        if (x < 1) throw new IllegalArgumentException("x must be at least 1");
        if (y < 1) throw new IllegalArgumentException("y must be at least 1");
        if (z < 1) throw new IllegalArgumentException("z must be at least 1");

        this.itemGrid = new ItemStackModel[z][y][x];
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public int getColumnCount() {
        return this.itemGrid[0][0].length;
    }

    public List<ItemStackModel> getInputItems() {
        if (this.inputItems == null) {
            this.inputItems = new TreeSet<ItemStackModel>();

            for (int z = 0; z < this.itemGrid.length; z++) {
                for (int y = 0; y < this.itemGrid[z].length; y++) {
                    for (int x = 0; x < this.itemGrid[z][y].length; x++) {
                        ItemStackModel stack = this.itemGrid[z][y][x];
                        if (stack == null) continue;
                        this.inputItems.add(stack);
                    }
                }
            }
        }
        return new ArrayList<ItemStackModel>(this.inputItems);
    }

    public ItemStackModel getItemStackAt(int x, int y, int z) {
        return this.itemGrid[z][y][x];
    }

    public void setItemStackAt(int x, int y, int z, ItemStackModel stack) {
        if (stack != null) {
            this.itemGrid[z][y][x] = new ItemStackModel(stack.getItem(), stack.getQuantity());
        } else {
            this.itemGrid[z][y][x] = null;
        }
        this.inputItems = null;
    }

    /**
     * Sets the item stack at all locations simultaneously. The grid is expected to be organized by a <z, y, x>
     * coordinate system (rather than <x, y, z> like other methods in this class) so that it looks "correct" when
     * written out using Java's array literal syntax. This method will automatically invert the order of things so that
     * the stacks are set into the proper locations.
     * 
     * @param items a 3-dimensional array of <tt>ItemStackModel</tt> instances arranged by <z, y, x> coordinates
     */
    public void setItemStacks(ItemStackModel[][][] items) {
        int layerCount = items.length;
        int rowCount = items[0].length;
        int columnCount = items[0][0].length;

        this.resize(columnCount, rowCount, layerCount);
        for (int layer = 0; layer < layerCount; layer++) {
            for (int row = 0; row < rowCount; row++) {
                for (int column = 0; column < columnCount; column++) {
                    this.setItemStackAt(column, row, layer, items[layer][row][column]);
                }
            }
        }

    }

    public int getLayerCount() {
        return this.itemGrid.length;
    }

    public int getRowCount() {
        return this.itemGrid[0].length;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ItemStackModel[][][] itemGrid = null;

    private Set<ItemStackModel> inputItems = null;
}
