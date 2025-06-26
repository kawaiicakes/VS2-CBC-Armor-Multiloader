package io.github.kawaiicakes.vscarmor.decal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// FIXME: Colour only updates on Fabric when an update is sent to client
public interface ColorableBlock extends EntityBlock {
    @Override
    @Nullable
    default BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ColorableBlockEntity(blockPos, blockState);
    }
}
