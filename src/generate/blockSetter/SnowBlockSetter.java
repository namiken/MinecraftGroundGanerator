package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class SnowBlockSetter extends GrassAndStoneSetter{
	@Override
	public void setBlock(Location add, int height) {
		super.setBlock(add, height);

		if (height + 1 < add.getWorld().getMaxHeight()) {
			add.setY(height + 1);
			add.getBlock().setType(Material.SNOW);
		}
	}
}
