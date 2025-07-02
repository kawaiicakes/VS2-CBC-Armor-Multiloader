package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ArmorBlock extends Block implements ColorableBlock {
    private final Grade grade;
    private final Pattern pattern;

    public ArmorBlock(Properties properties, Grade grade, Pattern pattern) {
        super(properties);
        this.grade = grade;
        this.pattern = pattern;
    }

    @Override
    public Grade getGrade() {
        return this.grade;
    }

    @Override
    public Type getType() {
        return Type.BLOCK;
    }

    @Override
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public void generateModelForType(BlockModelGenerators generator) {
        TextureMapping mapping = this.grade.getTextureMapping(this);

        ResourceLocation baseModel = ArmorModelTemplates.FULL_BLOCK.create(
                this,
                mapping,
                generator.modelOutput
        );
        ResourceLocation baseModelWl = ArmorModelTemplates.FULL_BLOCK.createWithSuffix(
                this,
                "_waterline",
                mapping,
                generator.modelOutput
        );

        // TODO - add more models dynamically based on # of layers in pattern
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(this)
                .with(
                        PropertyDispatch.property(WATERLINE)
                                .select(
                                        false,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModel)
                                )
                                .select(
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelWl)
                                )
                )
        );
    }
}
