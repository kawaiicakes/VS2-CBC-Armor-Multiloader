package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

public abstract class VSCArmorBlockLootTables {
    public static void generate(BlockLoot blockLoot) {
        for (Block block : VSCArmorRegistry.blocks()) {
            blockLoot.dropSelf(block);
        }
    }
}