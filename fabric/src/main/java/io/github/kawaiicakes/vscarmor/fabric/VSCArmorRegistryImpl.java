package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockItem;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.core.Registry.*;

public class VSCArmorRegistryImpl {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public static BlockEntityType<ColorableBlockEntity> COLORABLE_BE_TYPE = register(
            BLOCK_ENTITY_TYPE,
            "colorable",
            BlockEntityType.Builder.of(ColorableBlockEntity::new, VSCArmorRegistry.blocks()).build(null)
    );

    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        return COLORABLE_BE_TYPE;
    }

    private static <V, T extends V> T register(
            Registry<V> registry, String name, T object
    ) {
        return Registry.register(registry, new ResourceLocation(VSCArmor.MOD_ID, name), object);
    }

    private static Block getBlock(String name) {
        return BLOCK.get(new ResourceLocation(VSCArmor.MOD_ID, name));
    }

    public static Block[] blocks() {
        if (BLOCKS.isEmpty()) {
            for (String block : VSCArmorRegistry.ORDERED_BLOCK_NAMES) {
                BLOCKS.add(getBlock(block));
            }
        }

        return BLOCKS.toArray(Block[]::new);
    }

    public static void registerBlocksAndItems(Map<String, Supplier<Block>> forRegistration) {
        for (Map.Entry<String, Supplier<Block>> entry : forRegistration.entrySet()) {
            Block block = entry.getValue().get();

            register(BLOCK, entry.getKey(), block);
            register(ITEM, entry.getKey(), new ColorableBlockItem(block));
        }
    }
}
