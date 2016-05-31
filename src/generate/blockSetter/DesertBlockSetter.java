package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class DesertBlockSetter extends AbstractGroundBlockSetter{
	@Override
	public void setBlock(Location add, int height) {
		super.setBlock(add, height);
	}

	@Override
	protected Material getSurface() {
		return Material.SAND;
	}

	@Override
	protected Material getSurfaceUnder() {
		return Material.SANDSTONE;
	}

	@Override
	protected Material getUnderGround() {
		return Material.STONE;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setType(Location add, Material m) {
		super.setType(add, m);
		Material type = add.getBlock().getType();
		if (type == Material.SANDSTONE) {
			add.getBlock().setData((byte) r.nextInt(3));
		}
	}
}
