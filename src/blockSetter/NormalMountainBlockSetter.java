package blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class NormalMountainBlockSetter implements BlockSetterInterface {

	int maxHeight;
	public NormalMountainBlockSetter(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	@Override
	public void setBlock(Location add, int height) {
		for (int y = 0; y < add.getWorld().getMaxHeight(); y++) {
			add.setY(y);
			Material m = Material.AIR;
			if (y == 0 || y == 1) {
				m = Material.BEDROCK;
			} else if (height < 70) {
				if (y < height - 3) {
					m = Material.STONE;
				} else if (y < height) {
					m = Material.DIRT;
				} else if(y == height) {
					m = Material.GRASS;
				}
			} else {
				if (y <= height) {
					if (y <= maxHeight - 20) {
						m = Material.STONE;
					} else {
						m = Material.SNOW_BLOCK;
					}
				}
			}
			if (add.getBlock().getType() != m) {
				add.getBlock().setType(m);
			}
		}
	}

}
