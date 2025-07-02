package io.github.kawaiicakes.vscarmor.armor;

import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

public enum Pattern implements StringRepresentable {
    NONE(false),
    DESERT(true),
    FOREST(true),
    WOODLAND_POLYGON(true),
    GRAY_POLYGON(true),
    BUSH(true),
    ARCTIC(true),
    RAINBOW(false);

    private final boolean isCamo;
    private final byte layers;

    Pattern(boolean isCamo) {
        this(isCamo, (byte) 0);
    }

    Pattern(boolean isCamo, byte layers) {
        this.isCamo = isCamo;
        this.layers = layers;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.toString().toLowerCase();
    }

    public String asPrefix() {
        String toReturn = this.getSerializedName();

        return this.equals(NONE) ? "" : toReturn + "_";
    }

    @SuppressWarnings("deprecation")
    public String asPrettyPrefix() {
        String toReturn = this.getSerializedName();

        if (this.isCamo) toReturn = toReturn.replace("_", " ") + " Camo";

        return this.equals(NONE) ? "" : WordUtils.capitalize(toReturn) + " ";
    }

    public byte getLayers() {
        return this.layers;
    }
}