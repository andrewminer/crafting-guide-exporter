package com.craftingguide.exporter;

public interface Registry {

    public static enum Priority {
        HIGHEST, HIGH, MEDIUM, LOW, LOWEST
    };

    public void registerWorker(String classpath);

    public void registerWorker(String classpath, Priority priority);
}
