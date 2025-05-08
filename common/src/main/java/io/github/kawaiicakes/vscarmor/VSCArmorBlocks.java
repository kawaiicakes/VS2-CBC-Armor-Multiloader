package io.github.kawaiicakes.vscarmor;

import io.github.kawaiicakes.vscarmor.block.*;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK;

public class VSCArmorBlocks {
	public static List<Pair<String, Block>> BLOCKS = new ArrayList<>();

	public static Pattern[] patterns() {
		return new Pattern[] {
				new Pattern("", true),
				new Pattern("white", true),
				new Pattern("light_gray", true),
				new Pattern("gray", true),
				new Pattern("black", false),
				new Pattern("brown", true),
				new Pattern("red", true),
				new Pattern("orange", true),
				new Pattern("yellow", true),
				new Pattern("lime", true),
				new Pattern("green", true),
				new Pattern("cyan", true),
				new Pattern("light_blue", true),
				new Pattern("blue", true),
				new Pattern("purple", true),
				new Pattern("magenta", true),
				new Pattern("pink", true),
				new Pattern("4b0", true),
				new Pattern("29", true),
				new Pattern("31", true),
				new Pattern("32", true),
				new Pattern("33", true),
				new Pattern("dunkelgelb", true),
				new Pattern("mud", true),
				new Pattern("fern_green", true),
				new Pattern("panzergrau", true),
				new Pattern("rotbraun", true),
				new Pattern("ship_lower", true),
				new Pattern("stealth_gray", true),
				new Pattern("camo_desert", true),
				new Pattern("camo_forest", true),
				new Pattern("camo_woodland_polygon", true),
				new Pattern("camo_gray_polygon", true),
				/*
				new Pattern("camo_jungle", true),
                new Pattern("camo_mesa", true),
                new Pattern("camo_plains", true),
                new Pattern("camo_snow", true),
                new Pattern("camo_swamp", true),
                new Pattern("camo_taiga", true),
				 */
				new Pattern("camo_bush", true),
				new Pattern("camo_arctic", true),
				new Pattern("camo_rainbow", true),
		};
	}

	public static void init() {
		VSCArmor.LOGGER.info("Registering blocks for " + VSCArmor.NAME);

		for (Pattern pattern : patterns()) {
			BLOCKS.addAll(pattern.generateBlocks());
		}

		VSCArmorItems.init(BLOCKS);
	}

	public record Pattern (
		String name,
		boolean hasWlVariants
	) {
		public List<Pair<String, Block>> generateBlocks() {
			List<Pair<String, Block>> toReturn = new ArrayList<>();

			for (Grades grade : Grades.values()) {
				toReturn.add(generateEntry(grade, "", grade.block()));
				toReturn.add(generateEntry(grade, "_slab", grade.slab()));
				toReturn.add(generateEntry(grade, "_vertical_slab", grade.vSlab()));
				toReturn.add(generateEntry(grade, "_stairs", grade.stairs()));
				toReturn.add(generateEntry(grade, "_vertical_stairs", grade.vStairs()));
				toReturn.add(generateEntry(grade, "_fence", grade.fence()));
				toReturn.add(generateEntry(grade, "_wall", grade.wall()));
				toReturn.add(generateEntry(grade, "_porthole", grade.porthole()));
				toReturn.add(generateEntry(grade, "_porthole_slab", grade.portholeSlab()));
				toReturn.add(generateEntry(grade, "_porthole_vertical_slab", grade.portholeVSlab()));
				toReturn.add(generateEntry(grade, "_vertical_window", grade.vWindow()));
				toReturn.add(generateEntry(grade, "_vertical_window_slab", grade.vWindowSlab()));
				toReturn.add(generateEntry(grade, "_vertical_window_vertical_slab", grade.vWindowVSlab()));
				toReturn.add(generateEntry(grade, "_horizontal_window", grade.hWindow()));
				toReturn.add(generateEntry(grade, "_horizontal_window_slab", grade.hWindowSlab()));
				toReturn.add(generateEntry(grade, "_horizontal_window_vertical_slab", grade.hWindowVSlab()));

				if (!this.hasWlVariants) continue;

				toReturn.add(generateWlEntry(grade, "", grade.block()));
				toReturn.add(generateWlEntry(grade, "_slab", grade.slab()));
				toReturn.add(generateWlEntry(grade, "_vertical_slab", grade.vSlab()));
				toReturn.add(generateWlEntry(grade, "_stairs", grade.stairs()));
				toReturn.add(generateWlEntry(grade, "_vertical_stairs", grade.vStairs()));
				toReturn.add(generateWlEntry(grade, "_fence", grade.fence()));
				toReturn.add(generateWlEntry(grade, "_wall", grade.wall()));
				toReturn.add(generateWlEntry(grade, "_porthole", grade.porthole()));
				toReturn.add(generateWlEntry(grade, "_porthole_slab", grade.portholeSlab()));
				toReturn.add(generateWlEntry(grade, "_porthole_vertical_slab", grade.portholeVSlab()));
				toReturn.add(generateWlEntry(grade, "_vertical_window", grade.vWindow()));
				toReturn.add(generateWlEntry(grade, "_vertical_window_slab", grade.vWindowSlab()));
				toReturn.add(generateWlEntry(grade, "_vertical_window_vertical_slab", grade.vWindowVSlab()));
				toReturn.add(generateWlEntry(grade, "_horizontal_window", grade.hWindow()));
				toReturn.add(generateWlEntry(grade, "_horizontal_window_slab", grade.hWindowSlab()));
				toReturn.add(generateWlEntry(grade, "_horizontal_window_vertical_slab", grade.hWindowVSlab()));
			}

			return toReturn;
		}

		public String prefix() {
			return this.name.isEmpty()
					? ""
					: this.name + "_";
		}

		public Pair<String, Block> generateEntry(Grades grade, String suffix, Block block) {
			return Pair.of(prefix() + grade.name + suffix, block);
		}

		public Pair<String, Block> generateWlEntry(Grades grade, String suffix, Block block) {
			return Pair.of("wl_" + prefix() + grade.name + suffix, block);
		}
	}

	public enum Grades {
		LIGHT("light_armor", 3.0F, 5.0F, 392.0, 5, 1),
		STEEL("steel_armor", 10.0F, 7.0F, 1176.0, 7, 1),
		COMPOSITE("composite_armor", 28.0F, 8.0F, 2744.0, 8, 1),
		REINFORCED("reinforced_armor", 50.0F, 20.0F, 4312.0, 20, 1);

		final String name;
		final float hardness;
		final float blastResistance;
		final double mass;
		final float cbcToughness;
		final float cbcHardness;

		Grades(String name, float hardness, float blastResistance, double mass, float cbcToughness, float cbcHardness) {
			this.name = name;
			this.hardness = hardness;
			this.blastResistance = blastResistance;
			this.mass = mass;
			this.cbcToughness = cbcToughness;
			this.cbcHardness = cbcHardness;
		}

		public BlockBehaviour.Properties base() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.destroyTime(this.hardness)
					.explosionResistance(this.blastResistance);
		}

		public BlockBehaviour.Properties threeFourths() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.destroyTime(this.hardness * 0.75F)
					.explosionResistance(this.blastResistance * 0.75F);
		}

		public BlockBehaviour.Properties half() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.destroyTime(this.hardness * 0.5F)
					.explosionResistance(this.blastResistance * 0.5F);
		}

		public BlockBehaviour.Properties quarter() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.destroyTime(this.hardness * 0.25F)
					.explosionResistance(this.blastResistance * 0.25F);
		}

		public Block block() {
			return new Block(base());
		}

		public SlabBlock slab() {
			return new SlabBlock(half());
		}

		public VerticalSlabBlock vSlab() {
			return new VerticalSlabBlock(half());
		}

		public StairBlock stairs() {
			return new StairBlock(NETHERITE_BLOCK.defaultBlockState(), threeFourths());
		}

		public VerticalStairsBlock vStairs() {
			return new VerticalStairsBlock(NETHERITE_BLOCK::defaultBlockState, threeFourths());
		}

		public FenceBlock fence() {
			return new FenceBlock(quarter());
		}

		public WallBlock wall() {
			return new WallBlock(quarter());
		}

		public PortholeBlock porthole() {
			return new PortholeBlock(threeFourths());
		}

		public PortholeSlab portholeSlab() {
			return new PortholeSlab(quarter());
		}

		public PortholeVerticalSlab portholeVSlab() {
			return new PortholeVerticalSlab(quarter());
		}

		public VerticalWindowBlock vWindow() {
			return new VerticalWindowBlock(threeFourths());
		}

		public VerticalWindowSlab vWindowSlab() {
			return new VerticalWindowSlab(quarter());
		}

		public VerticalWindowVerticalSlab vWindowVSlab() {
			return new VerticalWindowVerticalSlab(quarter());
		}

		public HorizontalWindowBlock hWindow() {
			return new HorizontalWindowBlock(threeFourths());
		}

		public HorizontalWindowSlab hWindowSlab() {
			return new HorizontalWindowSlab(quarter());
		}

		public HorizontalWindowVerticalSlab hWindowVSlab() {
			return new HorizontalWindowVerticalSlab(quarter());
		}
	}
}
