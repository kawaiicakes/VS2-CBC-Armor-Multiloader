package io.github.kawaiicakes.vscarmor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO (2.0) - Update README with new 2.0.0 system
// TODO (2.0) - Add warnings for users with existing worlds / add an automatic replacement system
// TODO (2.0) - Life preservers
// TODO (2.0) - Tooltip includes armour stats
// TODO (2.0) - item display names should dynamically account for colours to make searching easier
// TODO (2.0) - Fix MapColors
// TODO (2.0) - the COMPRESSING (removal of as much registered stuff as possible; a single NBT-based block
// TODO (2.0) - Copycat and Framed Block support for the COMPRESSING; maybe make an interface for this
// TODO (2.0) - pending removal of waterline stuff for the COMPRESSING
// TODO (2.0) - Alphabet shit.
// TODO (2.0) - Hatches, bulkhead doors
// TODO (2.0) - Teak
// TODO (2.0) - Armoured decks

// TODO (2.1) - Allow armoured windows to break, leaving a hole in it
// TODO (2.1) - Sandbag
// TODO (2.1) - Reactive armour
// TODO (2.1) - Mesh fencing with edge-aligned placement options??? (probably no longer necessary post-COMPRESSING)
// TODO (2.1) - Connecting textures

// TODO (2.1?) - Borderless variants. Tiling is key.

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
