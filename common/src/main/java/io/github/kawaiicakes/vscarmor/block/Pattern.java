package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum Pattern implements StringRepresentable {
    NONE(""),
    DESERT("desert"),
    FOREST("forest"),
    WOODLAND_POLYGON("woodland_polygon"),
    GRAY_POLYGON("gray_polygon"),
    BUSH("bush"),
    ARCTIC("arctic"),
    RAINBOW("rainbow");

    private final String pattern;

    Pattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.pattern;
    }

    public String generateBlockName(Grade grade) {
        String prefix = this.getSerializedName().isBlank()
                ? ""
                : this.getSerializedName() + "_";

        return prefix + grade.getSerializedName();
    }
}