package io.github.kawaiicakes.vscarmor;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO (2.1) - Use a BE instead of 9 morbillion registered blocks
// TODO (2.0) - Eighths
// TODO (2.0) - Reactive armour
// TODO (2.0) - Alphabet shit.
// TODO (2.0) - Sandbag
// TODO (2.0) - Tooltip includes armour stats
// TODO (2.0) - Borderless variants. Tiling is key.
// TODO (2.0) - Porthole texture/model improvement. Infrastructure is already in place
// TODO (2.0) - LargeWindow
// TODO (2.0) - Hatches, bulkhead doors
// TODO (2.0) - Connecting textures
// TODO (2.0) - Life preservers
// TODO (2.0) - Teak
// TODO (2.0) - Armoured decks
// TODO (2.0) - Mesh fencing with edge-aligned placement options

public class VSCArmor {
    public static final String MOD_ID = "vscarmor";
    public static final String NAME = "VS2/CBC Armor Blocks";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    public static void init() {
        LOGGER.info(
                "{} initializing on platform: {}",
                NAME,
                VSCArmorExpectPlatform.platformName()
        );

        VSCArmorBlocks.init();
    }
}
