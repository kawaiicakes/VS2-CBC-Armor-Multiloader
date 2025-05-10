package io.github.kawaiicakes.vscarmor.forge.datagen;

import io.github.kawaiicakes.vscarmor.datagen.VSCArmorBlockTagProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class VSCArmorBlockTagsForge extends TagsProvider<Block> {
    protected final VSCArmorBlockTagProvider delegate;

    @SuppressWarnings("deprecation")
    public VSCArmorBlockTagsForge(DataGenerator output, String modId, ExistingFileHelper efh) {
        super(output, Registry.BLOCK, modId, efh);
        delegate = new VSCArmorBlockTagProvider(output) {
            @Override
            protected @NotNull TagBuilder getOrCreateRawBuilder(@NotNull TagKey<Block> arg) {
                return VSCArmorBlockTagsForge.this.getOrCreateRawBuilder(arg);
            }
        };
    }

    @Override
    protected void addTags() {
        this.delegate.addTags();
    }
}
