package io.github.kawaiicakes.vscarmor;

import dev.architectury.injectables.annotations.ExpectPlatform;
import io.github.kawaiicakes.vscarmor.block.ArmorBlock;
import io.github.kawaiicakes.vscarmor.armor.Grade;
import io.github.kawaiicakes.vscarmor.armor.Pattern;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

// TODO - Group blocks by Grade
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
                generateSeries(
                        pattern.asPrefix() + grade.getSerializedName(),
                        generated::put,
                        grade,
                        pattern
                );
            }
        }

        registerBlocksAndItems(generated);
    }

    public static void generateSeries(
            String seriesName,
            BiConsumer<String, Supplier<Block>> blockConsumer,
            Grade grade, Pattern pattern
    ) {
        ORDERED_BLOCK_NAMES.add(seriesName);
        blockConsumer.accept(seriesName, () -> new ArmorBlock(grade.properties(), grade, pattern));
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
