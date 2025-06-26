package io.github.kawaiicakes.vscarmor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO - Cleanup unused window breaking shit for now. It's an eyesore for development.
// TODO - Update README with new 2.0.0 system
// TODO - Add warnings for users with existing worlds / add an automatic replacement system
// TODO - Replace slab & vslab with slab that does both

// TODO - Life preservers
// TODO - Tooltip includes armour stats

// TODO (2.1) - Eighths
// TODO (2.1) - Reactive armour
// TODO (2.1) - Alphabet shit.
// TODO (2.1) - Sandbag
// TODO (2.1) - Hatches, bulkhead doors
// TODO (2.1) - Teak
// TODO (2.1) - Armoured decks
// TODO (2.1) - Mesh fencing with edge-aligned placement options
// TODO (2.1) - Reimplement breaking window stuff

// TODO (2.1?) - Borderless variants. Tiling is key.
// TODO (3.0) - Connecting textures

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

        VSCArmorRegistry.registerBlocksAndItems();
    }
}
