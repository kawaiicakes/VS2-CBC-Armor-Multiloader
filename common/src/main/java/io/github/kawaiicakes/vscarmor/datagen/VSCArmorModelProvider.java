package io.github.kawaiicakes.vscarmor.datagen;

import io.github.kawaiicakes.vscarmor.VSCArmorRegistry;
import io.github.kawaiicakes.vscarmor.armor.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

// TODO - make tint indexed models, waterline variants for those
/**
 * This isn't really intended to be implemented. It's just a bit of a hacky way to keep the actual model data in one
 * place lol
 */
public abstract class VSCArmorModelProvider extends ModelProvider {
    protected VSCArmorModelProvider(DataGenerator output) {
        super(output);
    }

    public static void generateModels(BlockModelGenerators generator) {
        for (Block block : VSCArmorRegistry.blocks()) {
            if (!(block instanceof ColorableBlock colorable)) continue;
            colorable.generateModelForType(generator);
        }
    }

    public static ResourceLocation withSuffixedPath(ResourceLocation target, String append) {
        return new ResourceLocation(target.getNamespace(), target.getPath() + append);
    }

    public static ResourceLocation withPrefixedPath(ResourceLocation target, String prepend) {
        return new ResourceLocation(target.getNamespace(), prepend + target.getPath());
    }
}
