package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.kawaiicakes.vscarmor.block.Grade;
import io.github.kawaiicakes.vscarmor.block.Pattern;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Do not make calls to methods in this class unless you are sure registration has completed
 */
public class VSCArmorRegistry {
    public static final List<String> ORDERED_BLOCK_NAMES = new ArrayList<>();

    /**
     * This passes a {@link Map} containing information of what to registerBlocksAndItems to the implementation of {@link #registerBlocksAndItems(Map)}.
     * This allows me to essentially declare stuff on the common side only
     */
    public static void registerBlocksAndItems() {
        Map<String, Supplier<Block>> generated = new HashMap<>();

        for (Grade grade : Grade.values()) {
            for (Pattern pattern : Pattern.values()) {
                String generatedName = pattern.generateBlockName(grade);
                ORDERED_BLOCK_NAMES.add(generatedName);
                grade.generateSeries(
                        (blockSupplier) -> generated.put(generatedName, blockSupplier)
                );
            }
        }

        registerBlocksAndItems(generated);
    }

    @ExpectPlatform
    public static Block[] blocks() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static BlockEntityType<ColorableBlockEntity> colorableBEType() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerBlocksAndItems(Map<String, Supplier<Block>> forRegistration) {
        throw new AssertionError();
    }
}
