package com.craftingguide.exporter.models;

import cpw.mods.fml.common.ModContainer;
import java.util.Collections;
import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;
import net.minecraftforge.fluids.FluidRegistry;

public class ModModel {

    public ModModel(ModContainer rawMod) {
        this(rawMod.getModId(), rawMod.getName(), rawMod.getVersion());
        this.setRawMod(rawMod);
    }

    public ModModel(String id, String displayName, String version) {
        this(id, displayName);
        this.setVersion(version);
    }

    public ModModel(String id, String displayName) {
        this.setDisplayName(displayName);
        this.setId(id);
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ModModel> SORT_BY_DISPLAY_NAME = new Comparator<ModModel>() {

        @Override
        public int compare(ModModel a, ModModel b) {
            return a.getDisplayName().compareTo(b.getDisplayName());
        }
    };

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean containsItemId(String itemId) {
        int index = itemId.indexOf(':');
        if (index == -1) return false;

        String modPrefix = itemId.substring(0, index);
        return modPrefix.equals(this.getId());
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAuthor() {
        if (this.author != null) return this.author;

        if (this.rawMod != null) {
            String authors = this.rawMod.getMetadata().getAuthorList();
            if (authors != null && authors.length() > 0) return authors;

            String credits = this.rawMod.getMetadata().credits;
            if (credits != null && credits.length() > 0) return credits;
        }

        return null;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        if (this.description != null) return this.description;

        if (this.rawMod != null) {
            String description = this.rawMod.getMetadata().description;
            if (description != null && description.length() > 0) return description;
        }

        return null;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    private void setDisplayName(String displayName) {
        if (displayName == null) throw new IllegalArgumentException("displayName cannot be null");
        this.displayName = displayName;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ItemModel getIconicBlock() {
        return this.iconicBlock;
    }

    public void setIconicBlock(ItemModel iconicBlock) {
        this.iconicBlock = iconicBlock;
    }

    public String getId() {
        return this.id;
    }

    private void setId(String id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        this.id = id;
    }

    public SortedSet<ItemModel> getAllItems() {
        return Collections.unmodifiableSortedSet(this.items);
    }

    public void addItem(ItemModel item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");
        if (this.items.contains(item)) return;

        if (item.isFluid()) {
            if (!this.containsItemId(FluidRegistry.getDefaultFluidName(item.getRawFluidStack().getFluid()))) {
                throw new IllegalArgumentException("item " + item.getId() + " does not belong to this mod");
            }
        } else {
            if (!this.containsItemId(item.getId())) {
                throw new IllegalArgumentException("item " + item.getId() + " does not belong to this mod");
            }
        }

        this.items.add(item);
        item.setMod(this);
    }

    public void removeItem(ItemModel item) {
        this.items.remove(item);
    }

    public ModContainer getRawMod() {
        return this.rawMod;
    }

    private void setRawMod(ModContainer rawMod) {
        this.rawMod = rawMod;
    }

    public String getUrl() {
        if (this.url != null) return this.url;

        if (this.rawMod != null) {
            String url = this.rawMod.getMetadata().url;
            if (url != null && url.length() > 0) return url;
        }

        return null;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return this.version;
    }

    private void setVersion(String version) {
        if (version == null) throw new IllegalArgumentException("version cannot be null");
        this.version = version;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private String author = null;

    private String description = null;

    private String displayName = null;

    private boolean enabled = false;

    private ItemModel iconicBlock = null;

    private String id = null;

    private SortedSet<ItemModel> items = new TreeSet<ItemModel>();

    private ModContainer rawMod = null;

    private String url = null;

    private String version = null;
}
