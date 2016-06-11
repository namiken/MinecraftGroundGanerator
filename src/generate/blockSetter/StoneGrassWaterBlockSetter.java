package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class StoneGrassWaterBlockSetter extends LiquidBlockSetter{

	@Override
	protected Material getLiquid(Location add, int height) {
		return Material.WATER;
	}

	@Override
	protected Material getLiquidButtom(Location add, int height) {
		return Material.STONE;
	}

	@Override
	protected Material getCoastline(Location add, int height) {
		return Material.SAND;
	}

	@Override
	protected Material getSurface(Location add, int height) {
		if (add.getY() < 70) {
			return Material.GRASS;
		}
		return Material.STONE;
	}

	@Override
	protected Material getSurfaceUnder(Location add, int height) {
		if (add.getY() < 70) {
			return Material.DIRT;
		}
		return Material.STONE;
	}

}
