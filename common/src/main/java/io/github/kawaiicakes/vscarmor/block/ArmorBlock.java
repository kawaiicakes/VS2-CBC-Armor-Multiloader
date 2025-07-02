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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ArmorBlock extends Block implements ColorableBlock {
    private final Grade grade;
    private final Pattern pattern;

    public ArmorBlock(Properties properties, Grade grade, Pattern pattern) {
        super(properties);
        this.grade = grade;
        this.pattern = pattern;
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLINE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLINE);
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

        // TODO - Use FULL_BLOCK for those with 0 layers, match template with corresponding layer count, also WL
        ResourceLocation baseModel = ArmorModelTemplates.FULL_BLOCK.create(
                this,
                mapping,
                generator.modelOutput
        );
        ResourceLocation baseModelWl = ArmorModelTemplates.FULL_BLOCK_WL.createWithSuffix(
                this,
                "_waterline",
                mapping,
                generator.modelOutput
        );

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(this)
                .with(
                        PropertyDispatch.property(WATERLINE)
                                .select(
                                        false,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModel)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                                .select(
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, baseModelWl)
                                                .with(VariantProperties.UV_LOCK, true)
                                )
                )
        );
    }
}
