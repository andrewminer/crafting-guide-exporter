package com.craftingguide.exporter.models;

import java.util.Comparator;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ItemStackModel implements Comparable<ItemStackModel> {

    public ItemStackModel(ItemStackModel that) {
        this(that.getItem(), that.getQuantity());
    }

    public ItemStackModel(ItemModel item, int quantity) {
        this.setItem(item);
        this.setQuantity(quantity);
    }

    // Class Methods ///////////////////////////////////////////////////////////////////////////////////////////////////

    public static ItemStackModel convert(ItemStack itemStack, ModPackModel modPack) {
        ItemModel item = modPack.getItem(itemStack);
        if (item == null) {
            ModModel modItemIsFrom = null;
            if (GameData.findModOwner(GameData.getItemRegistry().getNameForObject(itemStack.getItem())) == null) {
                modItemIsFrom = modPack.getMod("Minecraft");
            } else {
                for (ModModel mod : modPack.getAllMods()) {
                    if (GameData.findModOwner(GameData.getItemRegistry().getNameForObject(itemStack.getItem())).getModId() == mod.getId()) {
                        modItemIsFrom = mod;
                    }
                }
            }
            item = new ItemModel(GameData.getItemRegistry().getNameForObject(itemStack.getItem()), itemStack);
            modItemIsFrom.addItem(item);
            modPack.addItem(item);
        }

        return new ItemStackModel(item, itemStack.stackSize);
    }

    public static ItemStackModel convert(FluidStack fluidStack, ModPackModel context) {
        if (fluidStack == null) return null;

        ItemStackModel result = null;
        Block fluidBlock = null;

        if (fluidStack.getFluid() != null) {
            fluidBlock = fluidStack.getFluid().getBlock();
        }

        if (fluidBlock != null) {
            Item fluidItem = Item.getItemFromBlock(fluidBlock);
            ItemStack rawStack = new ItemStack(fluidItem, fluidStack.amount, 0);
            result = ItemStackModel.convert(rawStack, context);
        } else {
            ItemModel item = context.getItem(FluidRegistry.getDefaultFluidName(fluidStack.getFluid()));
            if (item == null) {
                item = new ItemModel(FluidRegistry.getDefaultFluidName(fluidStack.getFluid()), fluidStack);
                context.addItem(item);
            }
            result = new ItemStackModel(item, fluidStack.amount);
        }

        result.getItem().setRawFluidStack(fluidStack);
        return result;
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ItemStackModel> SORT_BY_DISPLAY_NAME = new Comparator<ItemStackModel>() {

        @Override
        public int compare(ItemStackModel a, ItemStackModel b) {
            if (!a.item.getDisplayName().equals(b.item.getDisplayName())) {
                return a.item.getDisplayName().compareTo(b.item.getDisplayName());
            }
            if (a.quantity != b.quantity) {
                return a.quantity > b.quantity ? -1 : +1;
            }
            return 0;
        }
    };

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ItemModel getItem() {
        return this.item;
    }

    private void setItem(ItemModel item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        this.item = item;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = (quantity < 0) ? 0 : quantity;
    }

    // Comparable Overrides ////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(ItemStackModel that) {
        if (!this.getItem().equals(that.getItem())) {
            return this.getItem().compareTo(that.getItem());
        }
        if (this.getQuantity() != that.getQuantity()) {
            return (this.getQuantity() < that.getQuantity()) ? -1 : +1;
        }
        return 0;
    }

    // Object Overrides ////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStackModel)) return false;
        ItemStackModel that = (ItemStackModel) obj;

        if (!this.item.equals(that.item)) return false;
        if (this.quantity != that.quantity) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.item.toString() + "*" + this.quantity;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ItemModel item;

    private int quantity;
}
