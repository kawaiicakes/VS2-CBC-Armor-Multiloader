package io.github.kawaiicakes.vscarmor.armor;

import net.minecraft.util.StringRepresentable;
import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

// TODO (2.0) - make this an interface maybe? maybe even a concrete class that works with data-driven entries?

/**
 * Defines what kind of {@link net.minecraft.world.level.block.Block} subclass something is. Probably defunct post-COMPRESSING.
 */
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
    public @NotNull String getSerializedName() {
        return this.equals(BLOCK) ? "" : this.toString().toLowerCase();
    }

    public String asSuffix() {
        String name = this.getSerializedName();
        return name.isBlank() ? "" : "_" + this.getSerializedName();
    }

    @SuppressWarnings("deprecation")
    public String asPrettySuffix() {
        if (this.equals(VSTAIRS)) return " Vertical Stairs";
        if (this.equals(BLOCK)) return "";
        return " " + WordUtils.capitalize(this.getSerializedName());
    }

    public float getPropertyMultiplier() {
        return this.propertyMultiplier;
    }
}
