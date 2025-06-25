package io.github.kawaiicakes.vscarmor.block;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static net.minecraft.world.level.block.Blocks.NETHERITE_BLOCK;

public enum Grades implements StringRepresentable {
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
					.sound(SoundType.METAL)
					.destroyTime(this.hardness)
					.explosionResistance(this.blastResistance);
		}

		public BlockBehaviour.Properties threeFourths() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.sound(SoundType.METAL)
					.destroyTime(this.hardness * 0.75F)
					.explosionResistance(this.blastResistance * 0.75F);
		}

		public BlockBehaviour.Properties half() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.sound(SoundType.METAL)
					.destroyTime(this.hardness * 0.5F)
					.explosionResistance(this.blastResistance * 0.5F);
		}

		public BlockBehaviour.Properties quarter() {
			return BlockBehaviour.Properties.copy(NETHERITE_BLOCK)
					.sound(SoundType.METAL)
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

	@Override
	public String getSerializedName() {
		return this.name;
	}
}