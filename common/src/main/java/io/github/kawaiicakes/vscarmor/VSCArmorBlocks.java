package io.github.kawaiicakes.vscarmor;

import com.simibubi.create.foundation.data.CreateRegistrate;

public class VSCArmorBlocks {
	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(VSCArmor.MOD_ID);

	/*
	public static final BlockEntry<ArmorBlock> LIGHT_ARMOR = REGISTRATE.block(
			"light_armor",
			(prop) -> new ArmorBlock(ArmorBlock.Grades.LIGHT.properties(), false)
	)
			.transform(ArmorBlock.transformer())
			.onRegister(CreateRegistrate.blockModel(() -> CopycatPanelModel::new))
			.item()
			.transform(customItemModel("copycat_base", "armor"))
			.register();
	 */

	public static void init() {
		// load the class and register everything
		VSCArmor.LOGGER.info("Registering blocks for " + VSCArmor.NAME);
		REGISTRATE.register();
	}
}
