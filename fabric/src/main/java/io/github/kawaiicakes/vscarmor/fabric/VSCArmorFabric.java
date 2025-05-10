package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmorItems;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorFabric implements ModInitializer, ClientModInitializer {
    public static CreativeModeTab TAB = FabricItemGroupBuilder
            .create(new ResourceLocation(MOD_ID, "vscarmor_group"))
            .icon(() -> Registry.ITEM.get(new ResourceLocation(MOD_ID, "light_armor")).getDefaultInstance())
            .build();

    @Override
    public void onInitialize() {
        VSCArmor.init();

        for (Pair<String, Supplier<Block>> pair : VSCArmorBlocks.BLOCKS) {
            Registry.register(Registry.BLOCK, new ResourceLocation(MOD_ID, pair.first()), pair.second().get());
        }

        for (Pair<String, Supplier<Item>> pair : VSCArmorItems.ITEMS) {
            Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, pair.first()), pair.second().get());
        }
    }

    @Override
    public void onInitializeClient() {

    }
}
