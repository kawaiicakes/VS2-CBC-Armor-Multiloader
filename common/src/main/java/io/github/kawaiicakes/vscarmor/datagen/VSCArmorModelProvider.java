package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.VerticalSlabBlock;
import io.github.kawaiicakes.vscarmor.block.VerticalStairsBlock;
import io.github.kawaiicakes.vscarmor.client.model.VerticalModels;
import io.github.kawaiicakes.vscarmor.decal.ColorableBlock;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.function.Function;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

// TODO - make models generate with tint indices
/**
 * This isn't really intended to be implemented. It's just a bit of a hacky way to keep the actual model data in one
 * place lol
 */
public abstract class VSCArmorModelProvider extends ModelProvider {
    protected VSCArmorModelProvider(DataGenerator output) {
        super(output);
    }

    public static void generateModels(BlockModelGenerators generator) {
        for (Block block : VSCArmorRegistry.blocks()) {
            ColorableBlock colorable = (ColorableBlock) block;

            ResourceLocation baseBlockModelId = TextureMapping.getBlockTexture(block);

            final TextureMapping map = TextureMapping.cube(baseBlockModelId)
                    .put(TextureSlot.SIDE, baseBlockModelId)
                    .put(TextureSlot.TOP, baseBlockModelId)
                    .put(TextureSlot.BOTTOM, baseBlockModelId)
                    .put(TextureSlot.END, baseBlockModelId)
                    .put(TextureSlot.TEXTURE, baseBlockModelId)
                    .put(TextureSlot.WALL, baseBlockModelId);

            generator.new BlockFamilyProvider(map)
                    .fullBlock(block, ModelTemplates.CUBE_ALL);
        }
        /*
        for (Grade grade : Grade.values()) {
            String pattern = grade.getSerializedName();
            ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);

            Block baseBlock = Registry.BLOCK.get(baseBlockId);
            Block slabBlock = Registry.BLOCK.get(withSuffixedPath(baseBlockId, "_slab"));
            Block stairsBlock = Registry.BLOCK.get(withSuffixedPath(baseBlockId, "_stairs"));
            Block wallBlock = Registry.BLOCK.get(withSuffixedPath(baseBlockId, "_wall"));
            Block fenceBlock = Registry.BLOCK.get(withSuffixedPath(baseBlockId, "_fence"));

            ResourceLocation baseBlockModelId = TextureMapping.getBlockTexture(baseBlock);

            final TextureMapping map = TextureMapping.cube(baseBlockModelId)
                    .put(TextureSlot.SIDE, baseBlockModelId)
                    .put(TextureSlot.TOP, baseBlockModelId)
                    .put(TextureSlot.BOTTOM, baseBlockModelId)
                    .put(TextureSlot.END, baseBlockModelId)
                    .put(TextureSlot.TEXTURE, baseBlockModelId)
                    .put(TextureSlot.WALL, baseBlockModelId);

            generator.new BlockFamilyProvider(map)
                    .fullBlock(baseBlock, ModelTemplates.CUBE_ALL)
                    .slab(slabBlock)
                    .fence(fenceBlock)
                    .stairs(stairsBlock)
                    .wall(wallBlock);

            createVerticalSlab(
                    generator,
                    pattern,
                    VSCArmorModelProvider::sideTopBottomSimple
            );
            createVerticalStairs(
                    generator,
                    pattern,
                    VSCArmorModelProvider::sideTopBottomSimple
            );

            createWindow(
                    "_porthole",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.PORTHOLE, ArmorBlockModels.PORTHOLE_EMPTY,
                    ArmorBlockModels.VERTICAL_PORTHOLE, ArmorBlockModels.VERTICAL_PORTHOLE_EMPTY
            );

            createWindowSlab(
                    "_porthole_slab", "_porthole_vertical",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.PORTHOLE_SLAB, ArmorBlockModels.PORTHOLE_SLAB_EMPTY,
                    ArmorBlockModels.PORTHOLE_SLAB_TOP, ArmorBlockModels.PORTHOLE_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_porthole_vertical_slab", "_porthole",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.PORTHOLE_VERTICAL_SLAB, ArmorBlockModels.PORTHOLE_VERTICAL_SLAB_EMPTY
            );

            createWindow(
                    "_vertical_window",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.VERTICAL_WINDOW, ArmorBlockModels.VERTICAL_WINDOW_EMPTY,
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL, ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_EMPTY
            );

            createWindowSlab(
                    "_vertical_window_slab", "_vertical_window_vertical",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.VERTICAL_WINDOW_SLAB, ArmorBlockModels.VERTICAL_WINDOW_SLAB_EMPTY,
                    ArmorBlockModels.VERTICAL_WINDOW_SLAB_TOP, ArmorBlockModels.VERTICAL_WINDOW_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_vertical_window_vertical_slab", "_vertical_window",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_SLAB,
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_SLAB_EMPTY
            );

            createWindow(
                    "_horizontal_window",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.HORIZONTAL_WINDOW, ArmorBlockModels.HORIZONTAL_WINDOW_EMPTY,
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL, ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_EMPTY
            );

            createWindowSlab(
                    "_horizontal_window_slab", "_horizontal_window_vertical",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.HORIZONTAL_WINDOW_SLAB, ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_EMPTY,
                    ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_TOP, ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_horizontal_window_vertical_slab", "_horizontal_window",
                    generator,
                    pattern,
                    block -> window(block, "", "", ""),
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_SLAB,
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_SLAB_EMPTY
            );
        }
         */
    }

    public static void createVerticalSlab(
            BlockModelGenerators generator, String pattern, Function<Block, TextureMapping> mapFunction
    ) {
        ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);
        Block baseBlock = Registry.BLOCK.get(baseBlockId);
        Block vSlabBlock = Registry.BLOCK.get(
                withSuffixedPath(baseBlockId, "_vertical_slab")
        );

        TextureMapping map = mapFunction.apply(baseBlock);

        ResourceLocation baseModelId = TextureMapping.getBlockTexture(baseBlock);
        ResourceLocation vSlabBlockModelId = VerticalModels.V_SLAB.create(vSlabBlock, map, generator.modelOutput);

        generator.blockStateOutput.accept(
                createVerticalSlabBlockstate(vSlabBlock, vSlabBlockModelId, baseModelId)
        );

        generator.delegateItemModel(vSlabBlock, vSlabBlockModelId);
    }

    public static void createVerticalStairs(
            BlockModelGenerators generator, String pattern, Function<Block, TextureMapping> mapFunction
    ) {
        ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);
        Block baseBlock = Registry.BLOCK.get(baseBlockId);
        Block vStairsBlock = Registry.BLOCK.get(
                withSuffixedPath(baseBlockId, "_vertical_stairs")
        );

        TextureMapping map = mapFunction.apply(baseBlock);

        ResourceLocation regularModelId = VerticalModels.V_STAIRS_STRAIGHT
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation innerModelBottomId = VerticalModels.V_STAIRS_INNER_BOTTOM
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation innerModelTopId = VerticalModels.V_STAIRS_INNER_TOP
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation outerModelRightBottomId = VerticalModels.V_STAIRS_OUTER_RIGHT_BOTTOM
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation outerModelRightTopId = VerticalModels.V_STAIRS_OUTER_RIGHT_TOP
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation outerModelLeftBottomId = VerticalModels.V_STAIRS_OUTER_LEFT_BOTTOM
                .create(vStairsBlock, map, generator.modelOutput);

        ResourceLocation outerModelLeftTopId = VerticalModels.V_STAIRS_OUTER_LEFT_TOP
                .create(vStairsBlock, map, generator.modelOutput);

        generator.blockStateOutput.accept(
                createVerticalStairsBlockstate(
                        vStairsBlock, regularModelId, innerModelTopId, innerModelBottomId,
                        outerModelRightBottomId, outerModelRightTopId,
                        outerModelLeftBottomId, outerModelLeftTopId
                )
        );

        generator.delegateItemModel(vStairsBlock, regularModelId);
    }

    /**
     * @param pattern also makes a reference to the properties block.
     * @param mapFunction this method passes the properties block to {@code Function<Block, TextureMapping>#apply}.
     */
    public static void createWindow(
            String windowSuffix,
            BlockModelGenerators generator, String pattern, Function<Block, TextureMapping> mapFunction,
            ModelTemplate windowBase, ModelTemplate emptyWindow, 
            ModelTemplate verticalWindow, ModelTemplate emptyVerticalWindow
    ) {
        ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);
        Block baseBlock = Registry.BLOCK.get(baseBlockId);
        Block portholeBlock = Registry.BLOCK.get(
                withSuffixedPath(baseBlockId, windowSuffix)
        );
        ResourceLocation blockModelId = windowBase.create(
                portholeBlock, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation emptyBlockModelId = emptyWindow.create(
                portholeBlock, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation verticalBlockModelId = verticalWindow.create(
                portholeBlock, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation verticalEmptyBlockModelId = emptyVerticalWindow.create(
                portholeBlock, mapFunction.apply(baseBlock), generator.modelOutput
        );

        generator.blockStateOutput.accept(
                createWindowBlockstate(
                        portholeBlock,
                        blockModelId, emptyBlockModelId,
                        verticalBlockModelId, verticalEmptyBlockModelId
                )
        );

        generator.delegateItemModel(portholeBlock, blockModelId);
    }

    public static void createWindowSlab(
            String windowSuffix, String suffix2,
            BlockModelGenerators generator, String pattern, Function<Block, TextureMapping> mapFunction,
            ModelTemplate windowBase, ModelTemplate emptyWindow, ModelTemplate topWindow, ModelTemplate emptyTopWindow
    ) {
        ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);
        Block baseBlock = Registry.BLOCK.get(baseBlockId);
        Block portholeSlab = Registry.BLOCK.get(
                withSuffixedPath(baseBlockId, windowSuffix)
        );


        ResourceLocation portholeDoubleModelId = TextureMapping.getBlockTexture(baseBlock, suffix2);

        ResourceLocation portholeDoubleEmptyModelId = TextureMapping.getBlockTexture(
                baseBlock, 
                suffix2 + "_empty"
        );

        ResourceLocation portholeSlabModelId = windowBase.create(
                portholeSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation portholeSlabEmptyModelId = emptyWindow.create(
                portholeSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation portholeSlabTopModelId = topWindow.create(
                portholeSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation portholeSlabTopEmptyModelId = emptyTopWindow.create(
                portholeSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );

        generator.blockStateOutput.accept(
                createWindowSlabBlockstate(
                        portholeSlab,
                        portholeDoubleModelId, portholeDoubleEmptyModelId,
                        portholeSlabModelId, portholeSlabEmptyModelId,
                        portholeSlabTopModelId, portholeSlabTopEmptyModelId
                )
        );
        
        generator.delegateItemModel(portholeSlab, portholeSlabModelId);
    }

    public static void createWindowVerticalSlab(
            String windowSuffix, String suffix2,
            BlockModelGenerators generator, String pattern, Function<Block, TextureMapping> mapFunction,
            ModelTemplate windowSlabModelId, ModelTemplate emptyWindowSlabModelId
    ) {
        ResourceLocation baseBlockId = new ResourceLocation(MOD_ID, pattern);
        Block baseBlock = Registry.BLOCK.get(baseBlockId);
        Block portholeVSlab = Registry.BLOCK.get(
                new ResourceLocation(baseBlockId.getNamespace(), baseBlockId.getPath() + windowSuffix)
        );

        ResourceLocation portholeDoubleModelId = TextureMapping.getBlockTexture(baseBlock, suffix2);
        ResourceLocation portholeDoubleEmptyModelId = TextureMapping.getBlockTexture(
                        baseBlock, 
                        suffix2 + "_empty"
                );

        ResourceLocation portholeSlabModelId = windowSlabModelId.create(
                portholeVSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );
        ResourceLocation portholeSlabEmptyModelId = emptyWindowSlabModelId.create(
                portholeVSlab, mapFunction.apply(baseBlock), generator.modelOutput
        );

        generator.blockStateOutput.accept(
                createWindowVerticalSlabBlockstate(
                        portholeVSlab,
                        portholeSlabModelId, portholeSlabEmptyModelId,
                        portholeDoubleModelId, portholeDoubleEmptyModelId
                )
        );

        generator.delegateItemModel(portholeVSlab, portholeSlabModelId);
    }

    public static BlockStateGenerator createVerticalSlabBlockstate(
            Block vSlabBlock, ResourceLocation vSlabBlockModelId, ResourceLocation baseModelId
    ) {
        return MultiVariantGenerator.multiVariant(vSlabBlock)
                .with(
                        PropertyDispatch
                                .properties(BlockStateProperties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLET)
                                .select(
                                        Direction.EAST, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.WEST, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.NORTH, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.EAST, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(Direction.WEST, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                                .select(Direction.NORTH, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelId)
                                )
                );
    }

    public static BlockStateGenerator createVerticalStairsBlockstate(
            Block vStairsBlock,
            ResourceLocation regularModelId, ResourceLocation innerModelTopId, ResourceLocation innerModelBottomId,
            ResourceLocation outerModelRightBottomId, ResourceLocation outerModelRightTopId,
            ResourceLocation outerModelLeftBottomId, ResourceLocation outerModelLeftTopId
    ) {
        return MultiVariantGenerator.multiVariant(vStairsBlock)
                .with(
                        PropertyDispatch
                                .properties(
                                        HorizontalDirectionalBlock.FACING,
                                        VerticalStairsBlock.HALF,
                                        VerticalStairsBlock.V_SHAPE
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightBottomId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightTopId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.MODEL, outerModelRightTopId)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelRightTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.RIGHT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftBottomId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftTopId)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.OUTER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerModelLeftTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_BOTTOM,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelBottomId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                )
                                .select(
                                        Direction.NORTH,
                                        VerticalStairsBlock.BlockHalf.LEFT,
                                        VerticalStairsBlock.VerticalStairShape.INNER_TOP,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerModelTopId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                );
    }

    public static BlockStateGenerator createWindowBlockstate(
            Block windowBlock,
            ResourceLocation windowModelId, ResourceLocation emptyWindowModelId,
            ResourceLocation verticalWindowModelId, ResourceLocation verticalEmptyWindowModelId
    ) {
        return MultiVariantGenerator.multiVariant(windowBlock)
                .with(
                        PropertyDispatch
                                .property(BlockStateProperties.AXIS)
                                .select(
                                        Direction.Axis.X,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, windowModelId)
                                )
                                .select(
                                        Direction.Axis.Y,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, verticalWindowModelId)
                                )
                                .select(
                                        Direction.Axis.Z,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, windowModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                );
    }

    public static BlockStateGenerator createWindowSlabBlockstate(
            Block windowBlock,
            ResourceLocation slabDoubleModel, ResourceLocation slabDoubleEmptyModel,
            ResourceLocation slabModel, ResourceLocation emptySlabModel,
            ResourceLocation slabTopModel, ResourceLocation slabEmptyTopModel
    ) {
        return MultiVariantGenerator.multiVariant(windowBlock)
                .with(
                        PropertyDispatch
                                .property(SlabBlock.TYPE)
                                .select(
                                        SlabType.BOTTOM,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, slabModel)
                                )
                                .select(
                                        SlabType.TOP,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, slabTopModel)
                                )
                                .select(
                                        SlabType.DOUBLE,
                                        Variant
                                                .variant()
                                                .with(VariantProperties.MODEL, slabDoubleModel)
                                )
                );
    }

    public static BlockStateGenerator createWindowVerticalSlabBlockstate(
            Block vSlabBlock,
            ResourceLocation vSlabBlockModelId, ResourceLocation vSlabBlockEmptyModelId,
            ResourceLocation doubleSlabModelId, ResourceLocation doubleSlabEmptyModelId
    ) {
        return MultiVariantGenerator.multiVariant(vSlabBlock)
                .with(
                        PropertyDispatch
                                .properties(BlockStateProperties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLET)
                                .select(
                                        Direction.EAST, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.WEST, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.NORTH, Boolean.FALSE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, vSlabBlockModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(
                                        Direction.EAST, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, doubleSlabModelId)
                                )
                                .select(
                                        Direction.SOUTH, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, doubleSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(Direction.WEST, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, doubleSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                                .select(Direction.NORTH, Boolean.TRUE,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, doubleSlabModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, Boolean.TRUE)
                                )
                );
    }

    public static TextureMapping sideTopBottomSimple(Block block) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
    }

    /**
     * If in the future better models need to be generated, the infrastructure is already in place here...
     */
    public static TextureMapping window(Block block, String windowBaseTextureSuffix, String top, String bottom) {
        ResourceLocation baseBlockTextureId = TextureMapping.getBlockTexture(block);
        ResourceLocation windowTextureId = withSuffixedPath(baseBlockTextureId, windowBaseTextureSuffix);
        ResourceLocation topTextureId = top.isEmpty()
                ? baseBlockTextureId
                : withPrefixedPath(new ResourceLocation(top), "block/");
        ResourceLocation bottomTextureId = bottom.isEmpty()
                ? baseBlockTextureId
                : withPrefixedPath(new ResourceLocation(bottom), "block/");

        return new TextureMapping()
                .put(TextureSlot.SIDE, baseBlockTextureId)
                .put(TextureSlot.TOP, topTextureId)
                .put(TextureSlot.BOTTOM, bottomTextureId)
                .put(TextureSlot.END, windowTextureId);
    }
    
    public static ResourceLocation withSuffixedPath(ResourceLocation target, String append) {
        return new ResourceLocation(target.getNamespace(), target.getPath() + append);
    }

    public static ResourceLocation withPrefixedPath(ResourceLocation target, String prepend) {
        return new ResourceLocation(target.getNamespace(), prepend + target.getPath());
    }
}
