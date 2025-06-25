package io.github.kawaiicakes.vscarmor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO - Models should specify tint indexes
// TODO - Clean up datagen
// TODO - Clean up registration (also, registry expect platforms should hold a list of blocks/items w/ a search method)
// TODO - Register block items with blocks automagically
// TODO - Redo Pattern system
// TODO - Grades should use method taking a float or something rather than #base, #quarter, etc.
// TODO - Cleanup unused window breaking shit for now. It's an eyesore for development. In the future, maybe replace it
//  with anonymous implementations of the abstract instead since it only needs a few overrides for hitbox stuff
// TODO - Update README with new 2.0.0 system
// TODO - Add warnings for users with existing worlds / add an automatic replacement system

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
    }
}
