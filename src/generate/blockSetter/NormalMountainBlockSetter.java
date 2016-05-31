package generate.blockSetter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

public class NormalMountainBlockSetter implements BlockSetterInterface {

	int maxHeight;
	public NormalMountainBlockSetter(int maxHeight) {
		this.maxHeight = maxHeight;
	}
	
	protected Random r = new Random();
	@Override
	public void setSeed(long seed) {
		r = new Random(seed);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(Location add, int height) {
		for (int y = 0; y < add.getWorld().getMaxHeight(); y++) {
			add.setY(y);
			add.getBlock().setTypeId(0);
			Material m = Material.AIR;
			if (y == 0 || y == 1) {
				m = Material.BEDROCK;
			} else if (height < maxHeight - 20) {
				if (y < height - 3) {
					m = Material.STONE;
				} else if (y < height) {
					m = Material.DIRT;
				} else if(y == height) {
					m = Material.GRASS;
				}
			} else {
				if (y <= height) {
					if (y > maxHeight - 17) {
						m = Material.SNOW_BLOCK;
					} else {
						//上に行けば行くほど雪の確立が高くなる
						int nextInt = r.nextInt(5);
						if (nextInt <= maxHeight - y - 20) {
							m = Material.SNOW_BLOCK;
						}
					}
					
				}
			}
			setType(add, m);
		}
	}

	protected void setType(Location add, Material m) {
		if (add.getBlock().getType() != m) {
			add.getBlock().setType(m);
		}
	}

}
