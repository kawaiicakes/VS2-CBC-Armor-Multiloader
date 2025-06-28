package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.VSCArmor;
import io.github.kawaiicakes.vscarmor.armor.Grade;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import java.util.HashMap;
import java.util.Map;

import static io.github.kawaiicakes.vscarmor.VSCArmor.MOD_ID;

public class VSCArmorFabric implements ModInitializer {
    public static final Map<Grade, CreativeModeTab> TABS = new HashMap<>();

    static {
        for (Grade grade : Grade.values()) {
            TABS.put(
                    grade,
                    FabricItemGroupBuilder
                            .create(new ResourceLocation(MOD_ID, "vscarmor." + grade.getSerializedName()))
                            .icon(
                                    () -> Registry.ITEM.get(new ResourceLocation(MOD_ID, grade.getSerializedName()))
                                            .getDefaultInstance()
                            )
                            .build()
            );
        }
    }

    @Override
    public void onInitialize() {
        VSCArmor.init();
    }
}
