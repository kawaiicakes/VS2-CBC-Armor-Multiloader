package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
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

// TODO - tweak and complete behaviour
@SuppressWarnings("deprecation")
public class UniversalSlabBlock extends DirectionalBlock implements SimpleWaterloggedBlock, ColorableBlock {
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

    public static final ModelTemplate UNIVERSAL_SLAB = ColorableBlock.block(
            "universal_slab",
            TextureSlot.ALL
    );
    public static final ModelTemplate UNIVERSAL_SLAB_WL = ColorableBlock.block(
            "universal_slab_waterline",
            TextureSlot.ALL,
            ArmorTextureSlots.WATERLINE
    );
    public static final ModelTemplate UNIVERSAL_SLAB_HORIZONTAL = ColorableBlock.block(
            "universal_slab_horizontal",
            TextureSlot.ALL
    );
    public static final ModelTemplate UNIVERSAL_SLAB_HORIZONTAL_WL = ColorableBlock.block(
            "universal_slab_horizontal_waterline",
            TextureSlot.ALL,
            ArmorTextureSlots.WATERLINE
    );

    protected final Grade grade;
    protected final Pattern pattern;

    public UniversalSlabBlock(Properties settings, Grade grade, Pattern pattern) {
        super(settings);
        this.registerDefaultState(
                this.defaultBlockState()
                        .setValue(FACING, Direction.DOWN)
                        .setValue(DOUBLET, Boolean.FALSE)
                        .setValue(WATERLOGGED, Boolean.FALSE)
                        .setValue(WATERLINE, Boolean.FALSE)
        );
        this.grade = grade;
        this.pattern = pattern;
    }

    @Override
    public boolean useShapeForLightOcclusion(BlockState pState) {
        return pState.getValue(DOUBLET);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, DOUBLET, WATERLOGGED, WATERLINE);
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

    @Override
    public Grade getGrade() {
        return this.grade;
    }

    @Override
    public Type getType() {
        return Type.SLAB;
    }

    @Override
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public void generateModelForType(BlockModelGenerators generator) {
        TextureMapping map = this.getGrade().getTextureMapping(this);

        ModelTemplate[] modelTemplates = {
            UNIVERSAL_SLAB
        };
        ModelTemplate[] modelWlTemplates = {
            UNIVERSAL_SLAB_WL
        };
        ModelTemplate[] hModelTemplates = {
                UNIVERSAL_SLAB_HORIZONTAL
        };
        ModelTemplate[] hModelWlTemplates = {
                UNIVERSAL_SLAB_HORIZONTAL_WL
        };

        ModelTemplate template = modelTemplates[this.pattern.getLayers()];
        ModelTemplate wlTemplate = modelWlTemplates[this.pattern.getLayers()];
        ModelTemplate hTemplate = hModelTemplates[this.pattern.getLayers()];
        ModelTemplate wlHTemplate = hModelWlTemplates[this.pattern.getLayers()];
        
        ResourceLocation slabModelId = template.create(this, map, generator.modelOutput);
        ResourceLocation wlSlabModelId = wlTemplate.create(
                ModelLocationUtils.getModelLocation(this, "_waterline"),
                map,
                generator.modelOutput
        );
        ResourceLocation hSlabModelId = hTemplate.create(
                ModelLocationUtils.getModelLocation(this, "_horizontal"),
                map,
                generator.modelOutput
        );
        ResourceLocation wlHSlabModelId = wlHTemplate.create(
                ModelLocationUtils.getModelLocation(this, "_horizontal_waterline"),
                map,
                generator.modelOutput
        );

        generator.blockStateOutput.accept(
                createUniversalSlabBlockstate(
                        this,
                        slabModelId, wlSlabModelId,
                        hSlabModelId, wlHSlabModelId,
                        this.grade, this
                )
        );

        generator.delegateItemModel(this, slabModelId);
    }

    public static BlockStateGenerator createUniversalSlabBlockstate(
            Block slab, 
            ResourceLocation slabBlockModelId, ResourceLocation wlSlabModelId,
            ResourceLocation hSlabBlockModelId, ResourceLocation wlHSlabModelId,
            Grade grade, ColorableBlock colorable
    ) {
        ResourceLocation baseModelId = grade.getGradeBaseModelId(colorable);
        ResourceLocation wlBaseModelId = grade.getGradeWlBaseModelId(colorable);
        
        return MultiVariantGenerator.multiVariant(slab)
                .with(
                        PropertyDispatch
                                .properties(
                                        FACING,
                                        UniversalSlabBlock.DOUBLET,
                                        WATERLINE
                                )
                                .select(
                                        Direction.UP, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, hSlabBlockModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.DOWN, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, hSlabBlockModelId)
                                )
                                .select(
                                        Direction.EAST, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, slabBlockModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, slabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.WEST, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, slabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.NORTH, Boolean.FALSE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, slabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.UP, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(
                                        Direction.DOWN, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(
                                        Direction.EAST, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(Direction.WEST, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(Direction.NORTH, Boolean.TRUE, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(
                                        Direction.UP, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, hSlabBlockModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.DOWN, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlHSlabModelId)
                                )
                                .select(
                                        Direction.EAST, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlSlabModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.WEST, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.NORTH, Boolean.FALSE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.UP, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                                .select(
                                        Direction.DOWN, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                                .select(
                                        Direction.EAST, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                                .select(Direction.WEST, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                                .select(Direction.NORTH, Boolean.TRUE, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, wlBaseModelId)
                                )
                );
    }
}
