package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Intended to easily define window behaviour as well as how windows should behave when broken.
 */
@SuppressWarnings("unused")
public interface WindowBlock {
    boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction);

    default RenderType getRenderLayer() {
        return RenderType.translucent();
    }

    VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context);

    default float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    default boolean isTransparent(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    /*
    TODO - windows should implement #getVisualShape (getCameraCollisionShape on Fabric), #skipRendering
        properties should also include these:
        .isValidSpawn(if glass is not facing up)
        .isViewBlocking(AbstractWindowBlock::ezPredicate)
        .isSuffocating(AbstractWindowBlock::ezPredicate)
     */
}
