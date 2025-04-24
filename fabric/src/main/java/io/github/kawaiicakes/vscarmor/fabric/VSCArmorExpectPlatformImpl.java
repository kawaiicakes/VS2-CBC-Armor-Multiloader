package io.github.kawaiicakes.vscarmor.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class VSCArmorExpectPlatformImpl {
	public static String platformName() {
		return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
	}
}
