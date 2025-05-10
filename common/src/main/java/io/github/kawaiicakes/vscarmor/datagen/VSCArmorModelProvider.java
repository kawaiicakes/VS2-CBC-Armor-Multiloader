package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.block.VerticalSlabBlock;
import io.github.kawaiicakes.vscarmor.block.VerticalStairsBlock;
import io.github.kawaiicakes.vscarmor.client.model.ArmorBlockModels;
import io.github.kawaiicakes.vscarmor.client.model.VerticalModels;
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
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public abstract class VSCArmorModelProvider extends ModelProvider {
    public VSCArmorModelProvider(DataGenerator output) {
        super(output);
    }

    public static void createSimpleModels(BlockModelGenerators generator) {
        for (String pattern : allBlockGradesAndPatternCombinations()) {
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
    }

    public static void createWaterlineModels(BlockModelGenerators generator) {
        for (String pattern : allBlockGradesAndPatternCombinations()) {
            if (pattern.startsWith("black_")) continue;

            ResourceLocation topPatternId = withPrefixedPath(new ResourceLocation(MOD_ID, pattern), "block/");
            ResourceLocation waterlineBaseId = new ResourceLocation(MOD_ID, "wl_" + pattern);

            String bottomPath = getWaterlineBottomPath(topPatternId);

            ResourceLocation blackPatternId = withPrefixedPath(
                    new ResourceLocation(MOD_ID, bottomPath), "block/"
            );

            Block baseBlock = Registry.BLOCK.get(waterlineBaseId);
            Block slabBlock = Registry.BLOCK.get(withSuffixedPath(waterlineBaseId, "_slab"));
            Block stairsBlock = Registry.BLOCK.get(withSuffixedPath(waterlineBaseId, "_stairs"));
            Block wallBlock = Registry.BLOCK.get(withSuffixedPath(waterlineBaseId, "_wall"));
            Block fenceBlock = Registry.BLOCK.get(withSuffixedPath(waterlineBaseId, "_fence"));

            ResourceLocation baseBlockModelId = TextureMapping.getBlockTexture(baseBlock);

            final TextureMapping map = TextureMapping.cube(baseBlockModelId)
                    .put(TextureSlot.SIDE, baseBlockModelId)
                    .put(TextureSlot.TOP, topPatternId)
                    .put(TextureSlot.BOTTOM, blackPatternId)
                    .put(TextureSlot.END, baseBlockModelId)
                    .put(TextureSlot.TEXTURE, baseBlockModelId)
                    .put(TextureSlot.WALL, baseBlockModelId);

            final TextureMapping invertedMap = TextureMapping.cube(baseBlockModelId)
                    .put(TextureSlot.SIDE, baseBlockModelId)
                    .put(TextureSlot.TOP, blackPatternId)
                    .put(TextureSlot.BOTTOM, topPatternId)
                    .put(TextureSlot.END, baseBlockModelId)
                    .put(TextureSlot.TEXTURE, baseBlockModelId)
                    .put(TextureSlot.WALL, baseBlockModelId);

            generator.new BlockFamilyProvider(map)
                    .fullBlock(baseBlock, ModelTemplates.CUBE_BOTTOM_TOP)
                    .slab(slabBlock);

            ResourceLocation innerBottomId
                    = ModelTemplates.STAIRS_INNER.create(stairsBlock, map, generator.modelOutput);
            ResourceLocation straightBottomId
                    = ModelTemplates.STAIRS_STRAIGHT.create(stairsBlock, map, generator.modelOutput);
            ResourceLocation outerBottomId
                    = ModelTemplates.STAIRS_OUTER.create(stairsBlock, map, generator.modelOutput);
            ResourceLocation innerTopId
                    = ArmorBlockModels.INNER_STAIRS_TOP.create(stairsBlock, invertedMap, generator.modelOutput);
            ResourceLocation straightTopId
                    = ArmorBlockModels.STAIRS_TOP.create(stairsBlock, invertedMap, generator.modelOutput);
            ResourceLocation outerTopId
                    = ArmorBlockModels.OUTER_STAIRS_TOP.create(stairsBlock, invertedMap, generator.modelOutput);
            generator.blockStateOutput.accept(
                    createWaterlineStairsBlockstate(
                            stairsBlock,
                            innerBottomId, straightBottomId, outerBottomId,
                            innerTopId, straightTopId, outerTopId
                    )
            );

            generator.delegateItemModel(stairsBlock, straightBottomId);

            ResourceLocation wallPostId
                    = ArmorBlockModels.TEMPLATE_WALL_POST.create(wallBlock, map, generator.modelOutput);
            ResourceLocation wallSideId
                    = ArmorBlockModels.TEMPLATE_WALL_SIDE.create(wallBlock, map, generator.modelOutput);
            ResourceLocation wallSideTallId
                    = ArmorBlockModels.TEMPLATE_WALL_SIDE_TALL.create(wallBlock, map, generator.modelOutput);

            generator.blockStateOutput.accept(
                    BlockModelGenerators.createWall(
                            wallBlock, wallPostId, wallSideId, wallSideTallId
                    )
            );

            ResourceLocation wallInventoryId
                    = ArmorBlockModels.WALL_INVENTORY.create(wallBlock, map, generator.modelOutput);
            generator.delegateItemModel(wallBlock, wallInventoryId);

            ResourceLocation fencePost = ArmorBlockModels.FENCE_POST.create(fenceBlock, map, generator.modelOutput);
            ResourceLocation fenceSide = ArmorBlockModels.FENCE_SIDE.create(fenceBlock, map, generator.modelOutput);
            generator.blockStateOutput.accept(
                    BlockModelGenerators.createFence(fenceBlock, fencePost, fenceSide)
            );
            ResourceLocation fenceInventory = ArmorBlockModels.FENCE_INVENTORY.create(
                    fenceBlock, map, generator.modelOutput
            );
            generator.delegateItemModel(fenceBlock, fenceInventory);

            createVerticalSlab(
                    generator,
                    "wl_" + pattern,
                    VSCArmorModelProvider::sideTopBottomWaterline
            );
            createVerticalStairs(
                    generator,
                    "wl_" + pattern,
                    VSCArmorModelProvider::sideTopBottomWaterline
            );

            createWindow(
                    "_porthole",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.PORTHOLE, ArmorBlockModels.PORTHOLE_EMPTY,
                    ArmorBlockModels.VERTICAL_PORTHOLE, ArmorBlockModels.VERTICAL_PORTHOLE_EMPTY
            );

            createWindowSlab(
                    "_porthole_slab", "_porthole_vertical",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.PORTHOLE_SLAB, ArmorBlockModels.PORTHOLE_SLAB_EMPTY,
                    ArmorBlockModels.PORTHOLE_SLAB_TOP, ArmorBlockModels.PORTHOLE_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_porthole_vertical_slab", "_porthole",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.PORTHOLE_VERTICAL_SLAB, ArmorBlockModels.PORTHOLE_VERTICAL_SLAB_EMPTY
            );

            createWindow(
                    "_vertical_window",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.VERTICAL_WINDOW, ArmorBlockModels.VERTICAL_WINDOW_EMPTY,
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL, ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_EMPTY
            );

            createWindowSlab(
                    "_vertical_window_slab", "_vertical_window_vertical",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.VERTICAL_WINDOW_SLAB, ArmorBlockModels.VERTICAL_WINDOW_SLAB_EMPTY,
                    ArmorBlockModels.VERTICAL_WINDOW_SLAB_TOP, ArmorBlockModels.VERTICAL_WINDOW_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_vertical_window_vertical_slab", "_vertical_window",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_SLAB,
                    ArmorBlockModels.VERTICAL_WINDOW_VERTICAL_SLAB_EMPTY
            );

            createWindow(
                    "_horizontal_window",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.HORIZONTAL_WINDOW, ArmorBlockModels.HORIZONTAL_WINDOW_EMPTY,
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL, ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_EMPTY
            );

            createWindowSlab(
                    "_horizontal_window_slab", "_horizontal_window_vertical",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.HORIZONTAL_WINDOW_SLAB, ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_EMPTY,
                    ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_TOP, ArmorBlockModels.HORIZONTAL_WINDOW_SLAB_TOP_EMPTY
            );

            createWindowVerticalSlab(
                    "_horizontal_window_vertical_slab", "_horizontal_window",
                    generator,
                    "wl_" + pattern,
                    block -> window(
                            block, "",
                            MOD_ID + ":" + pattern,
                            MOD_ID + ":" + getWaterlineBottomPath(Registry.BLOCK.getKey(block))
                    ),
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_SLAB,
                    ArmorBlockModels.HORIZONTAL_WINDOW_VERTICAL_SLAB_EMPTY
            );
        }
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
     * @param pattern also makes a reference to the base block.
     * @param mapFunction this method passes the base block to {@code Function<Block, TextureMapping>#apply}.
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

    public static BlockStateGenerator createWaterlineStairsBlockstate(
            Block stairsBlock,
            ResourceLocation innerBottomModelId, 
            ResourceLocation regularBottomModelId, 
            ResourceLocation outerBottomModelId,
            ResourceLocation innerTopModelId, ResourceLocation regularTopModelId, ResourceLocation outerTopModelId
    ) {
        return MultiVariantGenerator.multiVariant(stairsBlock)
                .with(
                        PropertyDispatch
                                .properties(
                                        BlockStateProperties.HORIZONTAL_FACING,
                                        StairBlock.HALF,
                                        StairBlock.SHAPE
                                )
                                .select(
                                        Direction.EAST, Half.BOTTOM, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularBottomModelId)
                                )
                                .select(
                                        Direction.WEST, Half.BOTTOM, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.BOTTOM, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.BOTTOM, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.BOTTOM, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                )
                                .select(
                                        Direction.WEST, Half.BOTTOM, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.BOTTOM, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.BOTTOM, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.BOTTOM, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                )
                                .select(
                                        Direction.NORTH, Half.BOTTOM, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.BOTTOM, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                )
                                .select(
                                        Direction.WEST, Half.BOTTOM, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.BOTTOM, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.BOTTOM, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.BOTTOM, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.BOTTOM, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                )
                                .select(
                                        Direction.NORTH, Half.BOTTOM, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerBottomModelId)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.TOP, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.TOP, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.TOP, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.TOP, StairsShape.STRAIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, regularTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.TOP, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.TOP, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.TOP, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.TOP, StairsShape.OUTER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.TOP, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.TOP, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.TOP, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.TOP, StairsShape.OUTER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, outerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.TOP, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.TOP, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.TOP, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.TOP, StairsShape.INNER_RIGHT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.EAST, Half.TOP, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.WEST, Half.TOP, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.SOUTH, Half.TOP, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        Direction.NORTH, Half.TOP, StairsShape.INNER_LEFT,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, innerTopModelId)
                                                .with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                );
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

    // TODO (1.1) - Implement empty blockstate
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

    // TODO (1.1) - Implement empty blockstate
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

    // TODO (1.1) - Implement empty blockstate
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

    public static String[] allBlockGradesAndPatternCombinations() {
        VSCArmorBlocks.Pattern[] patterns = VSCArmorBlocks.patterns();

        String[] toReturn = new String[patterns.length * 4];

        int colorIndex = 0;
        for (VSCArmorBlocks.Pattern pattern : patterns) {

            String prefix = pattern.prefix();

            toReturn[colorIndex * 4] = (prefix + "light_armor");
            toReturn[(colorIndex * 4) + 1] = (prefix + "steel_armor");
            toReturn[(colorIndex * 4) + 2] = (prefix + "composite_armor");
            toReturn[(colorIndex * 4) + 3] = (prefix + "reinforced_armor");

            colorIndex++;
        }

        return toReturn;
    }

    public static TextureMapping sideTopBottomSimple(Block block) {
        return new TextureMapping()
                .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block))
                .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block));
    }

    public static TextureMapping sideTopBottomWaterline(Block block) {
        String pattern = Registry.BLOCK.getKey(block).getPath();

        ResourceLocation topPatternId = withPrefixedPath(
                new ResourceLocation(MOD_ID, pattern.replaceFirst("wl_", "")), 
                "block/"
        );

        ResourceLocation waterlineBaseId = withPrefixedPath(new ResourceLocation(MOD_ID, pattern), "block/");

        String bottomPath = getWaterlineBottomPath(topPatternId);

        ResourceLocation blackPatternId = withPrefixedPath(new ResourceLocation(MOD_ID, bottomPath), "block/");

        return new TextureMapping()
                .put(TextureSlot.SIDE, waterlineBaseId)
                .put(TextureSlot.TOP, topPatternId)
                .put(TextureSlot.BOTTOM, blackPatternId);
    }

    @NotNull
    private static String getWaterlineBottomPath(ResourceLocation topPatternId) {
        String bottomPath = "black";
        if (topPatternId.getPath().contains("reinforced_armor")) {
            bottomPath += "_reinforced_armor";
        } else if (topPatternId.getPath().contains("light_armor")) {
            bottomPath += "_light_armor";
        } else if (topPatternId.getPath().contains("steel_armor")) {
            bottomPath += "_steel_armor";
        } else if (topPatternId.getPath().contains("composite_armor")) {
            bottomPath += "_composite_armor";
        }
        return bottomPath;
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
