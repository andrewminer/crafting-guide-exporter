package com.craftingguide.exporter;

public interface IRegistry {

    public static enum Priority {
        HIGHEST, HIGH, MEDIUM, LOW, LOWEST
    };

    public void registerDumper(IDumper dumper);

    public void registerDumper(IDumper dumper, Priority priority);

    public void registerEditor(IEditor editor);

    public void registerEditor(IEditor editor, Priority priority);

    public void registerGatherer(IGatherer gatherer);

    public void registerGatherer(IGatherer gatherer, Priority priority);
}
