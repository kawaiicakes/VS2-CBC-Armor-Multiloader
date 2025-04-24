package io.github.kawaiicakes.vscarmor.fabric;

import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import io.github.kawaiicakes.vscarmor.VSCArmorBlocks;
import io.github.kawaiicakes.vscarmor.VSCArmor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class VSCArmorFabric implements ModInitializer, ClientModInitializer {
    @Override
    public void onInitialize() {
        VSCArmor.init();
        VSCArmor.LOGGER.info(EnvExecutor.unsafeRunForDist(
                () -> () -> "{} is accessing Porting Lib on a Fabric client!",
                () -> () -> "{} is accessing Porting Lib on a Fabric server!"
                ), VSCArmor.NAME);
        // on fabric, Registrates must be explicitly finalized and registered.
        VSCArmorBlocks.REGISTRATE.register();
    }

    @Override
    public void onInitializeClient() {

    }
}
