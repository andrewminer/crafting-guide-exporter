package com.craftingguide.exporter.commands;

import com.craftingguide.CraftingGuideException;
import com.craftingguide.exporter.ExporterMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentTranslation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CraftingGuideDumpCommand extends CommandBase {

    public CraftingGuideDumpCommand(ExporterMod mod) {
        this.setMod(mod);
    }

    // Property Methods ////////////////////////////////////////////////////////////////////////////////////////////////

    public ExporterMod getMod() {
        return this.mod;
    }

    public void setMod(ExporterMod newMod) {
        if (newMod == null) throw new IllegalArgumentException("mod is required");
        this.mod = newMod;
    }

    // CommandBase Overrides ///////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "cgexport";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "cge.commands.cgexport.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            sender.addChatMessage(new ChatComponentTranslation("cge.commands.cgexport.start"));
            this.getMod().exportCraftingGuideData(()-> {
                sender.addChatMessage(new ChatComponentTranslation("cge.commands.cgexport.finish"));
            });
        } catch (CraftingGuideException e) {
            sender.addChatMessage(new ChatComponentTranslation("cge.commands.cgexport.error"));
            logger.error("Failed to complete the Crafting Guide export!", e);
        }
    }

    // Private Class Properties ////////////////////////////////////////////////////////////////////////////////////////

    private static Logger logger = LogManager.getLogger();

    // Private Properties //////////////////////////////////////////////////////////////////////////////////////////////

    private ExporterMod mod = null;
}
