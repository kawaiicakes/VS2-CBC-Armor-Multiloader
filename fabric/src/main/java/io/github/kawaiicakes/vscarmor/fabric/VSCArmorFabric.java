package io.github.kawaiicakes.vscarmor.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorItems;
import it.unimi.dsi.fastutil.Pair;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class VSCArmorFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        VSCArmor.init();
        VSCArmor.LOGGER.info(
                EnvExecutor.unsafeRunForDist(
                        () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                        () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ),
                VSCArmor.NAME
        );

        for (Pair<String, Block> pair : VSCArmorBlocks.BLOCKS) {
            Registry.register(Registry.BLOCK, pair.first(), pair.second());
        }

        for (Pair<String, Item> pair : VSCArmorItems.ITEMS) {
            Registry.register(Registry.ITEM, pair.first(), pair.second());
        }
    }

    @Override
    public void onInitializeClient() {

    }
}
