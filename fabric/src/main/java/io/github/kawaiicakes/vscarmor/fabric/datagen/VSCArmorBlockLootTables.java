package io.github.kawaiicakes.vscarmor.fabric.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.block.AbstractWindowSlab;
import io.github.kawaiicakes.vscarmor.block.AbstractWindowVerticalSlab;
import io.github.kawaiicakes.vscarmor.block.VerticalSlabBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class VSCArmorBlockLootTables extends FabricBlockLootTableProvider {
    public VSCArmorBlockLootTables(FabricDataGenerator dataOutput) {
        super(dataOutput);
    }

    public LootTable.Builder verticalSlabDrops(Block drop) {
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

    @Override
    public void generateBlockLootTables() {
        for (Block block : VSCArmorBlocks.blocks()) {
            if (block instanceof SlabBlock slab) {
                add(slab, createSlabItemTable(slab));
                continue;
            }

            if (block instanceof VerticalSlabBlock verticalSlab) {
                add(verticalSlab, verticalSlabDrops(verticalSlab));
                continue;
            }

            if (block instanceof AbstractWindowVerticalSlab verticalSlab) {
                add(verticalSlab, verticalSlabDrops(verticalSlab));
                continue;
            }

            if (block instanceof AbstractWindowSlab verticalSlab) {
                add(verticalSlab, createSlabItemTable(verticalSlab));
                continue;
            }

            add(block, createSlabItemTable(block));
        }
    }
}