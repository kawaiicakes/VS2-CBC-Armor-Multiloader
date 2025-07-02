package io.github.kawaiicakes.vscarmor.armor;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

// FIXME: Colour only updates when an update is sent to client
/**
 * Despite the name, this is implemented by any block from this mod that can take a pattern
 */
public interface ColorableBlock extends EntityBlock {
    BooleanProperty WATERLINE = BooleanProperty.create("waterline");

    static ModelTemplate block(String namespace, String parent, String variant, TextureSlot... requiredTextureSlots) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(namespace, "block/" + parent)),
                Optional.of(variant),
                requiredTextureSlots
        );
    }

    static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.empty(),
                requiredTextureKeys
        );
    }

    static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.of(variant),
                requiredTextureKeys
        );
    }

    @Override
    @Nullable
    default BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ColorableBlockEntity(blockPos, blockState);
    }

    default ResourceLocation getPatternBaseTexture() {
        return new ResourceLocation(
                VSCArmor.MOD_ID,
                this.getPattern().asPrefix() + this.getGrade().getSerializedName()
        );
    }

    Grade getGrade();

    Type getType();

    Pattern getPattern();

    /**
     * Implementations must use the passed {@link BlockModelGenerators} to output a model, with its blockstate, generated
     * based its {@link Grade}, {@link Pattern}, and {@link Type}. Remember to generate a waterline model for the blockstate, too.
     */
    void generateModelForType(BlockModelGenerators generator);
}
