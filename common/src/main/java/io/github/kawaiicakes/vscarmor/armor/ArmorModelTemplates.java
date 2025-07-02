package io.github.kawaiicakes.vscarmor.armor;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class ArmorModelTemplates {
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
    public static final ModelTemplate FULL_BLOCK = block(
            "basic_colorable",
            TextureSlot.ALL,
            ArmorTextureSlots.WATERLINE
    );

    public static final ModelTemplate UNIVERSAL_SLAB = block(
            "universal_slab",
            TextureSlot.ALL,
            ArmorTextureSlots.WATERLINE
    );

    public static ModelTemplate block(String namespace, String parent, String variant, TextureSlot... requiredTextureSlots) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(namespace, "block/" + parent)),
                Optional.of(variant),
                requiredTextureSlots
        );
    }

    public static ModelTemplate block(String parent, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.empty(),
                requiredTextureKeys
        );
    }

    public static ModelTemplate block(String parent, String variant, TextureSlot... requiredTextureKeys) {
        return new ModelTemplate(
                Optional.of(new ResourceLocation(MOD_ID, "block/" + parent)),
                Optional.of(variant),
                requiredTextureKeys
        );
    }
}
