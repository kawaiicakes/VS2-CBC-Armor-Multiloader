package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.block.ArmorBlock;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlock;
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

            String grade = ((ColorableBlock) block).getGrade().getSerializedName();

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

    public static boolean isFullBlock(Block block) {
        return block instanceof ArmorBlock;
    }

    public static boolean isWall(Block block) {
        return block instanceof WallBlock;
    }
}
