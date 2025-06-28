package io.github.kawaiicakes.vscarmor.fabric;

import io.github.kawaiicakes.vscarmor.armor.Grade;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.item.Item;

import static io.github.kawaiicakes.vscarmor.fabric.VSCArmorFabric.TABS;

@SuppressWarnings("unused")
public class VSCArmorExpectPlatformImpl {
	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}

	public static Item.Properties withTab(Item.Properties properties, Grade grade) {
		return properties.tab(TABS.get(grade));
	}

    public static String tabName(Grade grade) {
		return "itemGroup.vscarmor.vscarmor." + grade.getSerializedName();
    }
}
