package generate.blockSetter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

public abstract class AbstractGroundBlockSetter implements BlockSetterInterface{
	Random r = new Random();
	@Override
	public void setSeed(long seed) {
		r = new Random(seed);
	}

	
	@SuppressWarnings("deprecation")
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
			add.getBlock().setData((byte) 0);
			setType(add, m);
		}
	}

	public void setType(Location add, Material m) {
		if (add.getBlock().getType() != m) {
			add.getBlock().setType(m);
		}
	}

	abstract protected Material getSurface();

	abstract protected Material getSurfaceUnder();

	abstract protected Material getUnderGround();
}
