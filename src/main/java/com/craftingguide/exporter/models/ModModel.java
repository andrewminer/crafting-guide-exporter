package com.craftingguide.exporter.models;

import cpw.mods.fml.common.ModContainer;
import java.util.Comparator;

public class ModModel {

    public ModModel(ModContainer rawMod) {
        this(rawMod.getModId(), rawMod.getName(), rawMod.getVersion());
        this.setRawMod(rawMod);
    }

    public ModModel(String id, String displayName, String version) {
        this.setDisplayName(displayName);
        this.setId(id);
        this.setVersion(version);
    }

    // Class Properties ////////////////////////////////////////////////////////////////////////////////////////////////

    public static Comparator<ModModel> SORT_BY_DISPLAY_NAME = new Comparator<ModModel>() {

        @Override
        public int compare(ModModel a, ModModel b) {
            return a.displayName.compareTo(b.displayName);
        }
    };

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getAuthor() {
        if (this.author != null) return this.author;

        if (this.rawMod != null) {
            String authors = this.rawMod.getMetadata().getAuthorList();
            if (authors != null && authors.length() > 0) return authors;

            String credits = this.rawMod.getMetadata().credits;
            if (credits != null && credits.length() > 0) return credits;
        }

        return "";
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

        return "";
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

    public String getId() {
        return this.id;
    }

    private void setId(String id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        this.id = id;
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

        return "";
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

    private String id = null;

    private ModContainer rawMod = null;

    private String url = null;

    private String version = null;
}
