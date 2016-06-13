package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class OceanBlockSetter extends LiquidBlockSetter{

	@Override
	protected Material getLiquid(Location add, int height) {
		return Material.WATER;
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
		return Material.GRASS;
	}

	@Override
	protected Material getSurfaceUnder(Location add, int height) {
		return Material.DIRT;
	}

	@Override
	public void setMax(int max) {
	}

	@Override
	public void setMin(int min) {
	}

}
