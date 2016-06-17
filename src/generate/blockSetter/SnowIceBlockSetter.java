package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class SnowIceBlockSetter extends LiquidBlockSetter {

	@Override
	protected Material getLiquid(Location add, int height) {
		return Material.ICE;
	}

	@Override
	protected Material getLiquidButtom(Location add, int height) {
		return Material.GRAVEL;
	}

	@Override
	protected Material getCoastline(Location add, int height) {
		return Material.SAND;
	}

	@Override
	protected Material getSurface(Location add, int height) {
		return Material.STONE;
	}

	@Override
	protected Material getSurfaceUnder(Location add, int height) {
		return Material.STONE;
	}

	@Override
	public void setBlock(Location add, int height) {
		super.setBlock(add, height);

		if (height + 1 < add.getWorld().getMaxHeight() && height >= getSeaHeight()) {
			add.setY(height + 1);
			add.getBlock().setType(Material.SNOW);
		}
	}

	@Override
	public void setMax(int max) {
	}

	@Override
	public void setMin(int min) {
	}

}
