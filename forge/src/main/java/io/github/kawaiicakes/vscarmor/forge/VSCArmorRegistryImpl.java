package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockEntity;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class VSCArmorRegistryImpl {
    public static final List<Block> ORDERED_BLOCKS = new ArrayList<>();

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, VSCArmor.MOD_ID);

    public static final DeferredRegister<Item> ITEMS
            = DeferredRegister.create(ForgeRegistries.ITEMS, VSCArmor.MOD_ID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, VSCArmor.MOD_ID);

    public static final RegistryObject<BlockEntityType<ColorableBlockEntity>> COLORABLE_BE_TYPE = BLOCK_ENTITY_TYPES
            .register(
                    "colorable",
                    () -> BlockEntityType.Builder
                            .of(ColorableBlockEntity::new, VSCArmorRegistry.blocks())
                            .build(null)
            );

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        BLOCK_ENTITY_TYPES.register(modBus);
    }

    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        return COLORABLE_BE_TYPE.orElse(null);
    }

    public static Block[] blocks() {
        if (!ORDERED_BLOCKS.isEmpty()) return ORDERED_BLOCKS.toArray(Block[]::new);

        for (String block : VSCArmorRegistry.ORDERED_BLOCK_NAMES) {
            ORDERED_BLOCKS.add(
                    RegistryObject.create(new ResourceLocation(VSCArmor.MOD_ID, block), ForgeRegistries.BLOCKS).get()
            );
        }

        return ORDERED_BLOCKS.toArray(Block[]::new);
    }

    public static void registerBlocksAndItems(Map<String, Supplier<Block>> forRegistration) {
        for (Map.Entry<String, Supplier<Block>> entry : forRegistration.entrySet()) {
            BLOCKS.register(entry.getKey(), entry.getValue());
            ITEMS.register(
                    entry.getKey(),
                    () -> new ColorableBlockItem(
                            new Item.Properties(),
                            RegistryObject.create(
                                new ResourceLocation(VSCArmor.MOD_ID, entry.getKey()),
                                ForgeRegistries.BLOCKS
                            ).get()
                    )
            );
        }
    }
}
