package io.github.kawaiicakes.vscarmor.forge;

import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VSCArmor.MOD_ID)
public class VSCArmorForge {
    public VSCArmorForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        VSCArmorBlocks.REGISTRATE.registerEventListeners(eventBus);
        VSCArmor.init();
    }
}
