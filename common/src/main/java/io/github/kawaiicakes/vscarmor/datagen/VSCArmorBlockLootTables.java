package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.block.AbstractWindowSlab;
import io.github.kawaiicakes.vscarmor.block.AbstractWindowVerticalSlab;
import io.github.kawaiicakes.vscarmor.block.VerticalSlabBlock;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static net.minecraft.data.loot.BlockLoot.applyExplosionDecay;
import static net.minecraft.data.loot.BlockLoot.createSlabItemTable;

public abstract class VSCArmorBlockLootTables {
    public static LootTable.Builder verticalSlabDrops(Block drop) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(applyExplosionDecay(
                                drop,
                                LootItem.lootTableItem(drop).apply(
                                        SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(drop)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(VerticalSlabBlock.DOUBLET, true))
                                        )
                                )
                        ))
                );
    }

    public static void generate(BlockLoot blockLoot) {
        for (Block block : VSCArmorBlocks.blocks()) {
            if (block instanceof SlabBlock slab) {
                blockLoot.add(slab, createSlabItemTable(slab));
                continue;
            }

            if (block instanceof VerticalSlabBlock verticalSlab) {
                blockLoot.add(verticalSlab, verticalSlabDrops(verticalSlab));
                continue;
            }

            if (block instanceof AbstractWindowVerticalSlab verticalSlab) {
                blockLoot.add(verticalSlab, verticalSlabDrops(verticalSlab));
                continue;
            }

            if (block instanceof AbstractWindowSlab verticalSlab) {
                blockLoot.add(verticalSlab, createSlabItemTable(verticalSlab));
                continue;
            }

            blockLoot.dropSelf(block);
        }
    }
}