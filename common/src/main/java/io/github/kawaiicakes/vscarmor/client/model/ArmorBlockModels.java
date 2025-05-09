package io.github.kawaiicakes.vscarmor.client.model;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class ArmorBlockModels {
    public static final ModelTemplate INNER_STAIRS_TOP = block(
            "minecraft", "inner_stairs", "_inner_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate STAIRS_TOP = block(
            "minecraft", "stairs", "_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate OUTER_STAIRS_TOP = block(
            "minecraft", "outer_stairs", "_outer_top",
            TextureSlot.BOTTOM, TextureSlot.TOP, TextureSlot.SIDE
    );

    public static final ModelTemplate TEMPLATE_WALL_POST = block(
            MOD_ID, "template_wall_post", "_post", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate TEMPLATE_WALL_SIDE = block(
            MOD_ID, "template_wall_side", "_side", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate TEMPLATE_WALL_SIDE_TALL = block(
            MOD_ID, "template_wall_side_tall", "_side_tall",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate WALL_INVENTORY = block(
            MOD_ID, "wall_inventory", "_inventory", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE = block(
            MOD_ID, "porthole", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate PORTHOLE_EMPTY = block(
            MOD_ID, "porthole_empty", "_empty", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate VERTICAL_PORTHOLE = block(
            MOD_ID,
            "vertical_porthole", "_vertical",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_PORTHOLE_EMPTY = block(
            MOD_ID,
            "vertical_porthole_empty", "_vertical_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE_SLAB = block(
            MOD_ID,
            "porthole_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE_SLAB_TOP = block(
            MOD_ID,
            "porthole_slab_top", "_top",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE_SLAB_EMPTY = block(
            MOD_ID,
            "porthole_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE_SLAB_TOP_EMPTY = block(
            MOD_ID,
            "porthole_slab_top_empty", "_top_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate PORTHOLE_VERTICAL_SLAB = block(
            MOD_ID, "porthole_vertical_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate PORTHOLE_VERTICAL_SLAB_EMPTY = block(
            MOD_ID, "porthole_vertical_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate VERTICAL_WINDOW = block(
            MOD_ID, "vertical_window", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate VERTICAL_WINDOW_EMPTY = block(
            MOD_ID, "vertical_window_empty", "_empty", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate VERTICAL_WINDOW_VERTICAL = block(
            MOD_ID,
            "vertical_window_vertical", "_vertical",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_VERTICAL_EMPTY = block(
            MOD_ID,
            "vertical_window_vertical_empty", "_vertical_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_SLAB = block(
            MOD_ID,
            "vertical_window_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_SLAB_TOP = block(
            MOD_ID,
            "vertical_window_slab_top", "_top",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_SLAB_EMPTY = block(
            MOD_ID,
            "vertical_window_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_SLAB_TOP_EMPTY = block(
            MOD_ID,
            "vertical_window_slab_top_empty", "_top_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate VERTICAL_WINDOW_VERTICAL_SLAB = block(
            MOD_ID, "vertical_window_vertical_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate VERTICAL_WINDOW_VERTICAL_SLAB_EMPTY = block(
            MOD_ID, "vertical_window_vertical_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate HORIZONTAL_WINDOW = block(
            MOD_ID, "horizontal_window", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_EMPTY = block(
            MOD_ID, "horizontal_window_empty", "_empty", TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_VERTICAL = block(
            MOD_ID,
            "horizontal_window_vertical", "_vertical",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_VERTICAL_EMPTY = block(
            MOD_ID,
            "horizontal_window_vertical_empty", "_vertical_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_SLAB = block(
            MOD_ID,
            "horizontal_window_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_SLAB_TOP = block(
            MOD_ID,
            "horizontal_window_slab_top", "_top",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_SLAB_EMPTY = block(
            MOD_ID,
            "horizontal_window_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_SLAB_TOP_EMPTY = block(
            MOD_ID,
            "horizontal_window_slab_top_empty", "_top_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_VERTICAL_SLAB = block(
            MOD_ID, "horizontal_window_vertical_slab",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate HORIZONTAL_WINDOW_VERTICAL_SLAB_EMPTY = block(
            MOD_ID, "horizontal_window_vertical_slab_empty", "_empty",
            TextureSlot.SIDE, TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.END
    );

    public static final ModelTemplate FENCE_INVENTORY = block(
            MOD_ID, "fence_inventory", "_inventory",
            TextureSlot.TEXTURE, TextureSlot.TOP, TextureSlot.BOTTOM
    );
    public static final ModelTemplate FENCE_SIDE = block(
            MOD_ID, "fence_side", "_side",
            TextureSlot.TEXTURE, TextureSlot.TOP, TextureSlot.BOTTOM
    );
    public static final ModelTemplate FENCE_POST = block(
            MOD_ID, "fence_post", "_post",
            TextureSlot.TEXTURE, TextureSlot.TOP, TextureSlot.BOTTOM
    );

    private static ModelTemplate block(String namespace, String parent, String variant, TextureSlot... requiredTextureSlots) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(namespace, "block/" + parent)),
                Optional.of(variant),
                requiredTextureSlots
        );
    }

    private static ModelTemplate block(String namespace, String parent, TextureSlot... requiredTextureSlots) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(namespace, "block/" + parent)),
                Optional.empty(),
                requiredTextureSlots
        );
    }
}
