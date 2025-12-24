package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Block;

/**
 * Most basic implementation of {@link ColorableBlock}. Likely to be the only one unless something special is made in
 * the future.
 */
public class ArmorBlock extends Block implements ColorableBlock {
    public static final ModelTemplate FULL_BLOCK = ColorableBlock.block(
            "basic_colorable",
            TextureSlot.ALL
    );

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
    public Pattern getPattern() {
        return this.pattern;
    }

    @Override
    public void generateModelForType(BlockModelGenerators generator) {
        generator.createTrivialBlock(this, TexturedModel.createDefault(TextureMapping::cube, FULL_BLOCK));
    }
}
