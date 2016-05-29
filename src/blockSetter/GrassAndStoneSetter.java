package blockSetter;

import org.bukkit.Material;

public class GrassAndStoneSetter extends AbstractGroundBlockSetter{
	@Override
	protected Material getSurface() {
		return Material.GRASS;
	}

	@Override
	protected Material getSurfaceUnder() {
		return Material.DIRT;
	}

	@Override
	protected Material getUnderGround() {
		return Material.STONE;
	}
	
}
