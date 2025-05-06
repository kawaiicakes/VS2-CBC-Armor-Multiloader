package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK;

// TODO
public class ArmorBlock extends BaseEntityBlock {
    public final ArmorBlockProperties armorProperties;
    public final boolean window;

    public ArmorBlock(ArmorBlockProperties armorProperties, boolean window) {
        super(fromArmorProperties(armorProperties, window));
        this.armorProperties = armorProperties;
        this.window = window;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState blockState, BlockEntityType<T> blockEntityType
    ) {
        return null;
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public static Properties fromArmorProperties(ArmorBlockProperties armorProperties, boolean window) {
        Properties toReturn = Properties.copy(NETHERITE_BLOCK)
                .destroyTime(armorProperties.hardness())
                .explosionResistance(armorProperties.blastResistance());

        // TODO
        if (window) toReturn.isViewBlocking((a, b, c) -> false);

        return toReturn;
    }

    public record ArmorBlockProperties(
            String grade,
            float hardness,
            float blastResistance,
            float mass,
            float cbcHardness,
            float cbcToughness
    ) {}

    public enum Grades {
        LIGHT("light", 3.0F, 5.0F, 392.0F,  1.0F, 5.0F),
        STEEL("light", 10.0F, 7.0F, 1176.0F,  1.0F, 7.0F),
        COMPOSITE("light", 28.0F, 8.0F, 2744.0F,  1.0F, 8.0F),
        REINFORCED("light", 50.0F, 20.0F, 4312.0F,  1.0F, 20.0F);

        public final String grade;
        public final float hardness;
        public final float blastResistance;
        public final float mass;
        public final float cbcHardness;
        public final float cbcToughness;

        Grades(
                String grade,
                float hardness, float blastResistance,
                float mass,
                float cbcHardness, float cbcToughness
        ) {
            this.grade = grade;
            this.hardness = hardness;
            this.blastResistance = blastResistance;
            this.mass = mass;
            this.cbcHardness = cbcHardness;
            this.cbcToughness = cbcToughness;
        }

        public ArmorBlockProperties properties() {
            return new ArmorBlockProperties(
                    this.grade,
                    this.hardness,
                    this.blastResistance,
                    this.mass,
                    this.cbcHardness,
                    this.cbcToughness
            );
        }
    }
}
