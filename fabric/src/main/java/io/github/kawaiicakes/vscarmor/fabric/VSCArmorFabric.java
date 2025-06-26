package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorFabric implements ModInitializer {
    public static CreativeModeTab TAB = FabricItemGroupBuilder
            .create(new ResourceLocation(MOD_ID, "vscarmor_group"))
            .icon(() -> Registry.ITEM.get(new ResourceLocation(MOD_ID, "light_armor")).getDefaultInstance())
            .build();

    @Override
    public void onInitialize() {
        VSCArmor.init();
    }
}
