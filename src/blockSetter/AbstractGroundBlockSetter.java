package blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public abstract class AbstractGroundBlockSetter implements BlockSetterInterface{
	public void setBlock(Location add, int height) {
		int maxHeight = add.getWorld().getMaxHeight();
		for (int y = 0; y < maxHeight; y++) {
			add.setY(y);
			Material m = Material.AIR;
			if (y <= 1) {
				m = Material.BEDROCK;
			} else if (y < height - 3) {
				m = getUnderGround();
			} else if (y < height) {
				m = getSurfaceUnder();
			} else if (y == height) {
				m = getSurface();
			}
			if (add.getBlock().getType() != m) {
				add.getBlock().setType(m);
			}
		}
	}

	abstract protected Material getSurface();

	abstract protected Material getSurfaceUnder();

	abstract protected Material getUnderGround();
}
