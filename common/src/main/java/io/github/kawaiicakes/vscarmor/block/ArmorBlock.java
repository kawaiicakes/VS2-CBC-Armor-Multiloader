package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

public class ArmorBlock extends Block implements ColorableBlock {
    /*
                ModelTemplate#createWithSuffix or #create creates a ResourceLocation pointing to a model id based
                on the registry id of the block that is passed to it. A suffix may be appended after to create a
                ResourceLocation of the block's registry id + passed suffix.

                A ModelTemplate works primarily based around the BiConsumer argument in the aforementioned methods.
                The ModelTemplate holds a reference to the id of the parent model, then, when the BiConsumer (the
                model outputter from the BlockModelGenerators) is passed in, it generates a model, automatically
                filling in the texture variables specified inside the ModelTemplate according to the TextureMapping
                passed in.

                The model is generated, and the ResourceLocation pointing to it can then be used to generate the
                BlockState data. BlockModelGenerators work by creating models, and then creating BlockStates to which
                the models are assigned.
     */
    public static final ModelTemplate FULL_BLOCK = ColorableBlock.block(
            "full/basic_colorable",
            TextureSlot.ALL
    );
    public static final ModelTemplate FULL_BLOCK_WL = ColorableBlock.block(
            "full/basic_colorable_waterline",
            TextureSlot.ALL,
            ArmorTextureSlots.WATERLINE
    );

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

        final ModelTemplate[] templates = {
                FULL_BLOCK
        };
        final ModelTemplate[] wlTemplates = {
                FULL_BLOCK_WL
        };

        ModelTemplate baseModelTemplate = templates[this.pattern.getLayers()];
        ModelTemplate baseModelWlTemplate = wlTemplates[this.pattern.getLayers()];

        ResourceLocation baseModel = baseModelTemplate.create(
                this,
                mapping,
                generator.modelOutput
        );
        ResourceLocation baseModelWl = baseModelWlTemplate.createWithSuffix(
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
