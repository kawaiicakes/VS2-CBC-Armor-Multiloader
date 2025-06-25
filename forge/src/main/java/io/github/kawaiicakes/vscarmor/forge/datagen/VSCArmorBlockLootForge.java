package io.github.kawaiicakes.vscarmor.forge.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.datagen.VSCArmorBlockLootTables;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VSCArmorBlockLootForge extends LootTableProvider {
    protected final BlockLoot loot = new BlockLoot() {
        @Override
        public void accept(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> biConsumer) {
            Set<ResourceLocation> set = Sets.newHashSet();

            List<Block> blocks = List.of(VSCArmorRegistry.blocks());

            for (Block block : blocks) {
                ResourceLocation resourceLocation = block.getLootTable();
                if (resourceLocation != BuiltInLootTables.EMPTY && set.add(resourceLocation)) {
                    LootTable.Builder builder = this.map.remove(resourceLocation);
                    if (builder == null)
                        throw new IllegalStateException(
                                String.format(
                                        Locale.ROOT,
                                        "Missing loottable '%s' for '%s'",
                                        resourceLocation,
                                        ForgeRegistries.BLOCKS.getKey(block)
                                )
                        );


                    biConsumer.accept(resourceLocation, builder);
                }
            }

            if (!this.map.isEmpty()) {
                throw new IllegalStateException("Created block loot tables for non-blocks: " + this.map.keySet());
            }
        }
    };

    protected final Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>
    table;

    public VSCArmorBlockLootForge(DataGenerator arg) {
        super(arg);
        VSCArmorBlockLootTables.generate(this.loot);
        this.table = Pair.of(() -> this.loot, LootContextParamSets.BLOCK);
    }

    @Override
    @NotNull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>>
    getTables() {
        return ImmutableList.of(this.table);
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> tables, @NotNull ValidationContext ctx) {
        tables.forEach((name, table) -> LootTables.validate(ctx, name, table));
    }
}
