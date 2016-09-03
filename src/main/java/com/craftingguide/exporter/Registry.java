package com.craftingguide.exporter;

public interface Registry {

    public static enum Priority {
        HIGHEST, HIGH, MEDIUM, LOW, LOWEST
    };

    public void registerDumper(Dumper dumper);

    public void registerDumper(Dumper dumper, Priority priority);

    public void registerEditor(Editor editor);

    public void registerEditor(Editor editor, Priority priority);

    public void registerGatherer(Gatherer gatherer);

    public void registerGatherer(Gatherer gatherer, Priority priority);
}
