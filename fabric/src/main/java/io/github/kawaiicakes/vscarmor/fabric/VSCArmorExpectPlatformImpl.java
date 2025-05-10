package io.github.kawaiicakes.vscarmor.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;

import static io.github.kawaiicakes.vscarmor.fabric.VSCArmorFabric.TAB;

public class VSCArmorExpectPlatformImpl {
	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}

	public static Item.Properties withTab(Item.Properties properties) {
		return properties.tab(TAB);
	}

    public static String tabName() {
		return "itemGroup.vscarmor.vscarmor_group";
    }
}
