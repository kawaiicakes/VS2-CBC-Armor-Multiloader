package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmorItems;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

@Mod(MOD_ID)
public class VSCArmorForge {
    public static final DeferredRegister<Block> BLOCKS_FORGE = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS_FORGE = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public VSCArmorForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        VSCArmor.init();

        for (Pair<String, Block> pair : VSCArmorBlocks.BLOCKS) {
            BLOCKS_FORGE.register(pair.first(), pair::second);
        }

        for (Pair<String, Item> pair : VSCArmorItems.ITEMS) {
            ITEMS_FORGE.register(pair.first(), pair::second);
        }

        BLOCKS_FORGE.register(eventBus);
        ITEMS_FORGE.register(eventBus);
    }
}
