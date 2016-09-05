package com.craftingguide.exporter.models;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class CompoundModModel extends ModModel {

    public CompoundModModel(String id, String displayName) {
        super(id, displayName);
    }

    // Public Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean containsItemId(String itemId) {
        for (ModModel childMod : this.getAllChildMods()) {
            if (childMod.containsItemId(itemId)) return true;
        }
        return false;
    }

    public boolean isEmpty() {
        for (ModModel childMod : this.getAllChildMods()) {
            if (!childMod.isEmpty()) return false;
        }
        return true;
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAuthor() {
        String myAuthor = super.getAuthor();
        if (myAuthor != null) return myAuthor;

        ModModel primaryChild = this.getPrimaryChild();
        if (primaryChild != null) {
            return primaryChild.getAuthor();
        }

        return null;
    }

    public Set<ModModel> getAllChildMods() {
        return Collections.unmodifiableSet(this.childMods);
    }

    public void addChildMod(ModModel mod) {
        if (mod == null) return;
        this.childMods.add(mod);
    }

    public String getDescription() {
        String myDescription = super.getDescription();
        if (myDescription != null) return myDescription;

        ModModel primaryChild = this.getPrimaryChild();
        if (primaryChild != null) {
            return primaryChild.getDescription();
        }

        return null;
    }

    public SortedSet<ItemModel> getAllItems() {
        SortedSet<ItemModel> results = new TreeSet<>();
        for (ModModel childMod : this.getAllChildMods()) {
            results.addAll(childMod.getAllItems());
        }
        return results;
    }

    public void addItem(ItemModel item) {
        if (item == null) throw new IllegalArgumentException("item cannot be null");

        boolean foundMod = false;
        for (ModModel childMod : this.getAllChildMods()) {
            if (childMod.containsItemId(item.getId())) {
                childMod.addItem(item);
                foundMod = true;
            }
        }

        if (!foundMod) {
            throw new IllegalArgumentException("item " + item.getId() + " does not belong to this mod");
        }
    }

    public void removeItem(ItemModel item) {
        for (ModModel childMod : this.getAllChildMods()) {
            childMod.removeItem(item);
        }
    }

    public ModModel getPrimaryChild() {
        return this.primaryChild;
    }

    public void setPrimaryChild(ModModel mod) {
        this.primaryChild = mod;
        this.addChildMod(mod);
    }

    public String getUrl() {
        String myUrl = super.getUrl();
        if (myUrl != null) return myUrl;

        ModModel primaryChild = this.getPrimaryChild();
        if (primaryChild != null) {
            return primaryChild.getUrl();
        }

        return null;
    }

    public String getVersion() {
        String myVersion = super.getVersion();
        if (myVersion != null) return myVersion;

        ModModel primaryChild = this.getPrimaryChild();
        if (primaryChild != null) {
            return primaryChild.getVersion();
        }

        return null;
    }

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private Set<ModModel> childMods = new HashSet<>();

    private ModModel primaryChild;
}
