package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.UniversalSlabBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static net.minecraft.data.loot.BlockLoot.applyExplosionDecay;

public abstract class VSCArmorBlockLootTables {
    public static LootTable.Builder universalSlabBlocks(Block drop) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(applyExplosionDecay(
                                drop,
                                LootItem.lootTableItem(drop).apply(
                                        SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(drop)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(UniversalSlabBlock.DOUBLET, true))
                                        )
                                )
                        ))
                );
    }

    public static void generate(BlockLoot blockLoot) {
        for (Block block : VSCArmorRegistry.blocks()) {
            if (block instanceof UniversalSlabBlock slab) {
                blockLoot.add(slab, universalSlabBlocks(slab));
                continue;
            }

            blockLoot.dropSelf(block);
        }
    }
}