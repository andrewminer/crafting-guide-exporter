#!/bin/bash

JAR_FILE="crafting-guide-exporter-1.0.jar"
SOURCE_DIR="build/libs"
TARGET_DIR="$HOME/Documents/Minecraft/crafting-guide-1.7.10/mods"

gradle build
cp $SOURCE_DIR/$JAR_FILE $TARGET_DIR/$JAR_FILE
