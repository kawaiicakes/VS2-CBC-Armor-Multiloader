package io.github.kawaiicakes.vscarmor.armor;

import net.minecraft.core.BlockPos;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

// FIXME: Colour only updates when an update is sent to client
/**
 * Implementing blocks take on properties and appearance based on their {@link Grade} and {@link Pattern}.
 * They are also colourable with any arbitrary colour. Make sure to register instances of this to the loader's appropriate
 * colour provider.
 */
public interface ColorableBlock extends EntityBlock {
    /**
     *
     * @param namespace
     * @param parent
     * @param variant
     * @param requiredTextureSlots
     * @return
     */
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

    Grade getGrade();

    Pattern getPattern();

    /**
     * Implementations must use the passed {@link BlockModelGenerators} to output a model, with its blockstate, generated
     * based on its {@link Grade} and {@link Pattern}. Remember to generate a waterline model for the blockstate, too.
     */
    void generateModelForType(BlockModelGenerators generator);
}
