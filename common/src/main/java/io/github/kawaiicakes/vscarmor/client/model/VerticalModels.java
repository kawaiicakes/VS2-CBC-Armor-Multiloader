package io.github.kawaiicakes.vscarmor.client.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VerticalModels {
    public static final ModelTemplate V_SLAB = block(
            "vertical_slab",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_STRAIGHT = block(
            "vertical_stairs",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_INNER_BOTTOM = block(
            "inner_vertical_stairs_bottom", "_inner_bottom",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_INNER_TOP = block(
            "inner_vertical_stairs_top", "_inner_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_OUTER_RIGHT_BOTTOM = block(
            "outer_vertical_stairs_right_bottom", "_outer_right_bottom",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_OUTER_RIGHT_TOP = block(
            "outer_vertical_stairs_right_top", "_outer_right_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_OUTER_LEFT_BOTTOM = block(
            "outer_vertical_stairs_left_bottom", "_outer_left_bottom",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate V_STAIRS_OUTER_LEFT_TOP = block(
            "outer_vertical_stairs_left_top", "_outer_left_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    private static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.empty(),
                requiredTextureKeys
        );
    }

    private static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.of(variant),
                requiredTextureKeys
        );
    }
}
