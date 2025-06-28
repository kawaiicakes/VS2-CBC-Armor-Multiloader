package io.github.kawaiicakes.vscarmor.block;

import io.github.kawaiicakes.vscarmor.armor.Grade;
import io.github.kawaiicakes.vscarmor.armor.Pattern;
import io.github.kawaiicakes.vscarmor.armor.Type;
import io.github.kawaiicakes.vscarmor.armor.ColorableBlock;
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
