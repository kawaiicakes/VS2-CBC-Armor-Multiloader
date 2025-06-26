package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.text.WordUtils;

public enum Type implements StringRepresentable {
    BLOCK(1),
    SLAB(0.5F),
    STAIRS(0.75F),
    VSTAIRS(0.75F),
    FENCE(0.0625F),
    WALL(0.25F),
    PORTHOLE(0.75F),
    WINDOW(0.5F);

    private final float propertyMultiplier;

    Type(float propertyMultiplier) {
        this.propertyMultiplier = propertyMultiplier;
    }

    @Override
    public String getSerializedName() {
        return this.equals(BLOCK) ? "" : this.toString().toLowerCase();
    }

    public String asSuffix() {
        String name = this.getSerializedName();
        return name.isBlank() ? "" : "_" + this.getSerializedName();
    }

    @SuppressWarnings("deprecation")
    public String asPrettySuffix() {
        if (this.equals(VSTAIRS)) return " Vertical Stairs";
        return " " + WordUtils.capitalize(this.getSerializedName());
    }

    public float getPropertyMultiplier() {
        return this.propertyMultiplier;
    }
}
