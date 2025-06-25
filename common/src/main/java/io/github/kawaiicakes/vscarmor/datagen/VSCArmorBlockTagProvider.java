package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.VerticalSlabBlock;
import io.github.kawaiicakes.vscarmor.block.VerticalStairsBlock;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorBlockTagProvider extends BlockTagsProvider {
    public VSCArmorBlockTagProvider(DataGenerator output) {
        super(output);
    }

    @Override
    public void addTags() {
        TagBuilder pickaxeMineable = getOrCreateRawBuilder(BlockTags.MINEABLE_WITH_PICKAXE);
        TagBuilder beaconBase = getOrCreateRawBuilder(BlockTags.BEACON_BASE_BLOCKS);
        TagBuilder diamondTools = getOrCreateRawBuilder(BlockTags.NEEDS_DIAMOND_TOOL);
        TagBuilder witherImmune = getOrCreateRawBuilder(BlockTags.WITHER_IMMUNE);

        TagBuilder fenceBlocks = getOrCreateRawBuilder(BlockTags.FENCES);
        TagBuilder wallBlocks = getOrCreateRawBuilder(BlockTags.WALLS);

        for (Block block : VSCArmorRegistry.blocks()) {
            pickaxeMineable.addElement(Registry.BLOCK.getKey(block));
            diamondTools.addElement(Registry.BLOCK.getKey(block));
            witherImmune.addElement(Registry.BLOCK.getKey(block));

            if (isFullBlock(block)) {
                beaconBase.addElement(Registry.BLOCK.getKey(block));
            }

            String blockPath = Registry.BLOCK.getKey(block).getPath();

            String grade = getGrade(blockPath);

            TagBuilder gradeTag = getOrCreateRawBuilder(
                    TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(MOD_ID, grade))
            );

            gradeTag.addElement(Registry.BLOCK.getKey(block));

            if (isWall(block))
                wallBlocks.addElement(Registry.BLOCK.getKey(block));

            if (block instanceof FenceBlock)
                fenceBlocks.addElement(Registry.BLOCK.getKey(block));
        }
    }

    public static String getGrade(String blockPath) {
        String grade;

        if (blockPath.contains("light_armor")) grade = "light_armor";
        else if (blockPath.contains("steel_armor")) grade = "steel_armor";
        else if (blockPath.contains("composite_armor")) grade = "composite_armor";
        else grade = "reinforced_armor";

        // looks for vertical and horizontal too instead of just window in prep for full window block
        if (blockPath.contains("vertical_window") || blockPath.contains("horizontal_window"))
            grade += "_window_slits";

        if (blockPath.contains("porthole"))
            grade += "_porthole";

        if (blockPath.contains("slab"))
            grade += "_slab";
        else if (blockPath.contains("stairs"))
            grade += "_stairs";
        else if (blockPath.contains("fence"))
            grade += "_fence";
        else if (blockPath.contains("wall"))
            grade += "_wall";
        return grade;
    }

    public static boolean isFullBlock(Block block) {
        return !(block instanceof SlabBlock)
                && !(block instanceof VerticalSlabBlock)
                && !(block instanceof StairBlock)
                && !(block instanceof VerticalStairsBlock);
    }

    public static boolean isWall(Block block) {
        return block instanceof WallBlock;
    }
}
