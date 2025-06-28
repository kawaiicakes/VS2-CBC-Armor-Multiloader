package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

@SuppressWarnings("deprecation")
public class UniversalSlabBlock extends DirectionalBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty DOUBLET = BooleanProperty.create("doublet");
    public static final VoxelShape NORTH = Block.box(
            0.0, 0.0, 0.0,
            16.0, 16.0, 8.0
    );
    public static final VoxelShape EAST = Block.box(
            8.0, 0.0, 0.0,
            16.0, 16.0, 16.0
    );
    public static final VoxelShape SOUTH = Block.box(
            0.0, 0.0, 8.0,
            16.0, 16.0, 16.0
    );
    public static final VoxelShape WEST = Block.box(
            0.0, 0.0, 0.0,
            8.0, 16.0, 16.0
    );
    public static final VoxelShape UP = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
    public static final VoxelShape DOWN = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);

    public UniversalSlabBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(DOUBLET, Boolean.FALSE)
                        .setValue(WATERLOGGED, Boolean.FALSE)
        );
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return pState.getValue(DOUBLET);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, DOUBLET, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(DOUBLET)
                ? Shapes.block()
                : switch (state.getValue(FACING)) {
                        case NORTH -> NORTH;
                        case SOUTH -> SOUTH;
                        case WEST -> WEST;
                        case EAST -> EAST;
                        case UP -> UP;
                        case DOWN -> DOWN;
                };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockAtPos = ctx.getLevel().getBlockState(blockPos);

        if (blockAtPos.is(this)) {
            return blockAtPos.setValue(DOUBLET, Boolean.TRUE).setValue(WATERLOGGED, Boolean.FALSE);
        } else {
            FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
            BlockState blockState2 = this.defaultBlockState()
                    .setValue(DOUBLET, Boolean.FALSE)
                    .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);

            // TODO - tweak and complete this behaviour
            boolean isUp = (ctx.getClickLocation().y - ctx.getClickedPos().getY()) > 0.5;
            double horizontalCoordOnBlock;
            final double oneThird = (double) 1 / 3;
            final double twoThirds = (double) 2 / 3;

            if (ctx.getHorizontalDirection().getAxis() == Direction.Axis.X) {
                horizontalCoordOnBlock = ctx.getClickLocation().x - (double) ctx.getClickedPos().getX();

                if (horizontalCoordOnBlock < oneThird) {
                    return blockState2.setValue(FACING, Direction.WEST);
                } else if (horizontalCoordOnBlock >= oneThird && horizontalCoordOnBlock < twoThirds) {
                    return blockState2.setValue(FACING, isUp ? Direction.UP : Direction.DOWN);
                } else {
                    return blockState2.setValue(FACING, Direction.EAST);
                }
            } else {
                horizontalCoordOnBlock = ctx.getClickLocation().z - (double) ctx.getClickedPos().getZ();

                if (horizontalCoordOnBlock < oneThird) {
                    return blockState2.setValue(FACING, Direction.NORTH);
                } else if (horizontalCoordOnBlock >= oneThird && horizontalCoordOnBlock < twoThirds) {
                    return blockState2.setValue(FACING, isUp ? Direction.UP : Direction.DOWN);
                } else {
                    return blockState2.setValue(FACING, Direction.SOUTH);
                }
            }
        }
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        ItemStack itemStack = context.getItemInHand();
        Boolean doublet = state.getValue(DOUBLET);
        Direction facing = state.getValue(FACING);

        if (doublet || !itemStack.is(this.asItem())) {
            return false;
        } else if (context.replacingClickedOnBlock()) {
            return context.getClickedFace() == facing.getOpposite();
        } else {
            return true;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        return !pState.getValue(DOUBLET) && SimpleWaterloggedBlock.super.placeLiquid(pLevel, pPos, pState, pFluidState);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter pLevel, BlockPos pPos, BlockState pState, Fluid pFluid) {
        return !pState.getValue(DOUBLET) && SimpleWaterloggedBlock.super.canPlaceLiquid(pLevel, pPos, pState, pFluid);
    }

    @Override
    public BlockState updateShape(
            BlockState state, Direction pDirection,
            BlockState pNeighborState,
            LevelAccessor world,
            BlockPos pos, BlockPos pNeighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, pDirection, pNeighborState, world, pos, pNeighborPos);
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return switch (pType) {
            case LAND, AIR -> false;
            case WATER -> pLevel.getFluidState(pPos).is(FluidTags.WATER);
        };
    }
}
