package io.github.kawaiicakes.vscarmor.decal;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * This class is really more of just a formality/reminder to add pattern stuff to blocks. Implementations should extend
 * {@link net.minecraft.world.level.block.Block}
 */
public interface PatternBlock {
    IntegerProperty PATTERN = IntegerProperty.create("pattern", 0, Pattern.totalNumber());
    BooleanProperty WATERLINE = BooleanProperty.create("waterline");

    /**
     * Use inside implementing Block's constructor when calling #registerDefaultState
     * @param state the BlockState from this#defaultBlockState
     */
    default BlockState createDefaultState(BlockState state) {
        return state
                .setValue(PATTERN, 0)
                .setValue(WATERLINE, false);
    }

    /**
     * Use in #createBlockStateDefinition and pass the builder
     */
    default void addPatternStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PATTERN);
        builder.add(WATERLINE);
    }

    enum Pattern implements StringRepresentable {
        NONE(""),
        DESERT("desert"),
        FOREST("forest"),
        WOODLAND_POLYGON("woodland_polygon"),
        GRAY_POLYGON("gray_polygon"),
        BUSH("bush"),
        ARCTIC("arctic"),
        RAINBOW("rainbow");

        private final String pattern;

        Pattern(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.pattern;
        }

        public static Pattern fromString(String pattern) {
            return Arrays.stream(Pattern.values())
                    .filter(value -> value.getSerializedName().equals(pattern))
                    .findFirst()
                    .orElseThrow();
        }

        public static int totalNumber() {
            return Pattern.values().length - 1;
        }
    }
}
