package com.craftingguide.exporter.models;

public class MultiblockModel {

    public MultiblockModel() {
        this.resize(3, 3, 3);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public void resize(int x, int y, int z) {
        if (x < 1) throw new IllegalArgumentException("x must be at least 1");
        if (y < 1) throw new IllegalArgumentException("y must be at least 1");
        if (z < 1) throw new IllegalArgumentException("z must be at least 1");

        this.itemGrid = new ItemStackModel[x][y][z];
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getGroupName() {
        return this.groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ItemStackModel getItemAt(int x, int y, int z) {
        return this.itemGrid[x][y][z];
    }

    public void setItemAt(int x, int y, int z, ItemModel item) {
        this.setItemAt(x, y, z, new ItemStackModel(item, 1));
    }

    public void setItemAt(int x, int y, int z, ItemStackModel stack) {
        if (stack != null) {
            this.itemGrid[x][y][z] = new ItemStackModel(stack.getItem(), stack.getQuantity());
        } else {
            this.itemGrid[x][y][z] = null;
        }
    }

    public void setItems(ItemStackModel[][][] items) {
        int width = items.length;
        int height = items[0].length;
        int depth = items[0][0].length;

        this.resize(width, height, depth);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    this.setItemAt(x, y, z, items[x][y][z]);
                }
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null) throw new IllegalArgumentException("name cannot be null");
        this.name = name;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ItemStackModel[][][] itemGrid = null;

    private String groupName = null;

    private String name = "Untitled";
}
