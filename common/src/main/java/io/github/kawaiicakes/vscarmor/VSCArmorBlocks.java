package io.github.kawaiicakes.vscarmor;

import com.simibubi.create.foundation.data.CreateRegistrate;

public class VSCArmorBlocks {
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(VSCArmor.MOD_ID);

	public static void init() {
		// load the class and register everything
		VSCArmor.LOGGER.info("Registering blocks for " + VSCArmor.NAME);
	}
}
