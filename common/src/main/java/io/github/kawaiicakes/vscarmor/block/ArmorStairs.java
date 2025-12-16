package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

import java.util.function.Supplier;

public class ArmorStairs extends StairBlock implements ColorableBlock {
    public static final ModelTemplate STAIRS = ColorableBlock.block(
            "stairs/basic_colorable",
            TextureSlot.BOTTOM,
            TextureSlot.TOP,
            TextureSlot.SIDE
    );
    public static final ModelTemplate STAIRS_WL = ColorableBlock.block(
            "stairs/basic_colorable_waterline",
            TextureSlot.BOTTOM,
            TextureSlot.TOP,
            TextureSlot.SIDE,
            ArmorTextureSlots.WATERLINE
    );

    private final Grade grade;
    private final Pattern pattern;

    public ArmorStairs(Supplier<Block> blockSupplier, Properties properties, Grade grade, Pattern pattern) {
        super(blockSupplier.get().defaultBlockState(), properties);
        this.grade = grade;
        this.pattern = pattern;
    }

    @Override
    public Grade getGrade() {
        return this.grade;
    }

    @Override
    public Type getType() {
        return Type.STAIRS;
    }

    @Override
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public void generateModelForType(BlockModelGenerators generator) {
        TextureMapping map = this.getGrade().getTextureMapping(this);

        ModelTemplate[] modelTemplates = {
                STAIRS
        };
        ModelTemplate[] modelWlTemplates = {
                STAIRS_WL
        };

        ModelTemplate template = modelTemplates[this.pattern.getLayers()];
        ModelTemplate wlTemplate = modelWlTemplates[this.pattern.getLayers()];

        ResourceLocation stairsModelId = template.create(this, map, generator.modelOutput);

        ResourceLocation wlStairsModelId = wlTemplate.create(this, map, generator.modelOutput);

        generator.blockStateOutput.accept(
                BlockModelGenerators.createStairs(
                        this, stairsModelId, wlStairsModelId, resourceLocation3
                )
        );

        generator.delegateItemModel(this, stairsModelId);
    }
}
