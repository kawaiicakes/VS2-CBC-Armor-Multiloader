package io.github.kawaiicakes.vscarmor.mixin;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.client.model.generators.ModelProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;

@Mixin(ModelProvider.class)
public interface ModelProviderAccessor {
    @Accessor(remap = false)
    <T extends ModelBuilder<T>> Function<ResourceLocation, T> getFactory();
}
