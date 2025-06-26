package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.decal.ColorableBlock;
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
}
