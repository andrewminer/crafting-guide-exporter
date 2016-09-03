package com.craftingguide.exporter.extensions.minecraft;

import com.craftingguide.exporter.baseWorkers.GroupAssignmentEditor;

public class MinecraftGroupEditor extends GroupAssignmentEditor {

    public MinecraftGroupEditor() {
        this.addPattern("Armor", ".*Armor|.*Boots|.*Cap|.*Chestplate|.*Helm(et)?|.*Leggings|.*Pants|.*Tunic");
        this.addPattern("Blocks (earthen)", ".*Clay|Dirt|Farmland|Gravel|Mycelium|Nether Brick.*|Podzol|.*Sand.*");
        this.addPattern("Blocks (glass)", ".*Glass.*");
        this.addPattern("Blocks (minerals)", "");
        this.addPattern("Blocks (tools)", "Anvil|Beacon|Bed|Cauldron|.*Chest|Command.*|Crafting Table|Furnace|Jukebox");
        this.addPattern("Blocks (stone)", "Bedrock|Chiseled.*|.*Cobble.*|Netherrack|.*Quartz.*|.*Stone.*|Obsidian");
        this.addPattern("Blocks (wooden)", ".*Fence.*|.*Wood (Planks|Slab|Stairs)");
        this.addPattern("Brewing (potions)", ".*Potion.*");
        this.addPattern("Brewing (supplies)", "Brewing Stand|Ghast Tear|Golden Carrot|Gunpowder|Magma Cream");
        this.addPattern("Brewing (supplies)", "Nether Wart");
        this.addPattern("Carpets", ".*Carpet");
        this.addPattern("Decorative", "Cobweb|Fire|Iron Bars|.*Lantern|.*Portal|Sign|.*Spawner|Sponge");
        this.addPattern("Dyes", "Cocoa Beans|.*Dye");
        this.addPattern("Fireworks", "Fire Charge|Firework.*");
        this.addPattern("Flowers", "Allium|.*Bluet|.*Orchid|.*Daisy|Dandelion.*|Lilac|Peony|Poppy|Sunflower|.*Tulip");
        this.addPattern("Food", ".*Apple|Bread|Cake|Carrot|Cooked.*|Cookie|.*[fF]ish|Hay.*|Melon.*|Milk|Mushroom.*");
        this.addPattern("Food", ".*Potato|Pumpkin.*|Raw.*|.*Seeds|Steak|Sugar.*|Wheat");
        this.addPattern("Household Items", ".*Book.*|.*Bottle.*|Bowl|.*Disc.*|.*Door|Flower Pot|.*Frame|Ladder");
        this.addPattern("Household Items", "Painting|Paper");
        this.addPattern("Liquids", ".*Bucket|Lava|Water");
        this.addPattern("Machines", "Button|Dispenser|Dropper|Hopper|Lever|.*Piston|.*Pressure Plate.*|Redstone .*");
        this.addPattern("Machines", ".*Sensor|Trapdoor|Tripwire.*");
        this.addPattern("Minerals", ".*Block|Block of.*|.*[cC]oal|Diamond|Emerald|Flint|Glowstone.*|.*Ice|.*Ingot");
        this.addPattern("Minerals", ".*Nugget|Redstone|Snow.*");
        this.addPattern("Minerals", "Nether Quartz|.*Ore");
        this.addPattern("Mob Drops (animals)", "Egg|Feather|Leather|.*Wool");
        this.addPattern("Mob Drops (monsters)", "Blaze.*|.*Bone.*|Dragon.*|.*Ender.*|.*Eye|.*Flesh|.*Head|Nether Star");
        this.addPattern("Mob Drops (monsters)", ".*Skull|Slimeball|String");
        this.addPattern("Plants", "Cactus|.*Bush|.*Fern|.*[gG]rass|Lily Pad|Mushroom.*block.*|Vines");
        this.addPattern("Transportation", "Boat|Minecart.*|.*Rail");
        this.addPattern("Trees", ".*(Leaves|Sapling|Wood)|Stick");
        this.addPattern("Tools", ".*Axe|.*Hoe|Carrot on a Stick|Clock|Compass|Fishing Rod|Flint and Steel|Lead|.*Map");
        this.addPattern("Tools", "Name Tag|.*Pickaxe|Saddle|Shears|.*Shovel|TNT|Torch");
        this.addPattern("Weapons", "Arrow|Bow|.*Sword");

        // Second Tier (overrides)
        this.addPattern("Brewing (supplies)", "Awkward Potion|Fermented.*|Glistering.*|Mundane Potion|Potent Potion");
        this.addPattern("Brewing (supplies)", "Thick Potion");
        this.addPattern("Blocks (earthen)", "Brick.*");
        this.addPattern("Dyes", "Bone Meal|Cactus Green|Dandelion Yellow|Ink Sac|Lapis Lazuli|Rose Red");
        this.addPattern("Enchanting", "Enchant.*");
        this.addPattern("Flowers", "Rose Bush");
    }
}
