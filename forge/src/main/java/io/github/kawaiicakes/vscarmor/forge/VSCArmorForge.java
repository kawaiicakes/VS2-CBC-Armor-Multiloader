package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmorItems;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

@Mod(MOD_ID)
public class VSCArmorForge {
    public static final DeferredRegister<Block> BLOCKS_FORGE = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS_FORGE = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    public static final CreativeModeTab TAB = new CreativeModeTab("vscarmor_group") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return RegistryObject.create(new ResourceLocation(MOD_ID, "light_armor"), ForgeRegistries.ITEMS)
                    .get()
                    .getDefaultInstance();
        }
    };

    public VSCArmorForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        VSCArmor.init();

        for (Pair<String, Supplier<Block>> pair : VSCArmorBlocks.BLOCKS) {
            BLOCKS_FORGE.register(pair.first(), pair.second());
        }

        for (Pair<String, Supplier<Item>> pair : VSCArmorItems.ITEMS) {
            ITEMS_FORGE.register(pair.first(), pair.second());
        }

        BLOCKS_FORGE.register(eventBus);
        ITEMS_FORGE.register(eventBus);
    }
}
