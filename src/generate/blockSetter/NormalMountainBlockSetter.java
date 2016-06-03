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
			} else if (height < y) {
				m = Material.AIR;
			} else if (height < 70){
				//芝生
				if (y < height - 3) {
					m = Material.STONE;
				} else if (y < height) {
					m = Material.DIRT;
				} else if(y == height) {
					m = Material.GRASS;
				}
			//石
			} else if (height < maxHeight - 17) {
				m = Material.STONE;
			//雪
			} else  {
				if (y == height) {

				}
				m = Material.SNOW_BLOCK;
			}

//			}
//			} else if (70 < y) {
//				m = Material.STONE;
//			} else if (y < maxHeight - 20) {
//				if (y < height - 3) {
//					m = Material.STONE;
////					System.out.println(y + "@1.1");
//				} else if (y < height) {
//					m = Material.DIRT;
////					System.out.println(y + "@1.2");
//				} else if(y == height) {
//					m = Material.GRASS;
////					System.out.println(y + "@1.3");
//				} else {
////					System.out.println(y + "@1.4");
//				}
//			} else {
////				System.out.println(y + "@2@" + ( maxHeight - 20));
//				if (y <= height) {
//					if (y > maxHeight - 17) {
//						m = Material.SNOW_BLOCK;
//					} else {
//						//上に行けば行くほど雪の確立が高くなる
//						int nextInt = r.nextInt(5);
//						if (nextInt <= maxHeight - y - 20) {
//							m = Material.SNOW_BLOCK;
//						} else {
//							m = Material.STONE;
//						}
//					}
//
//				}
//			}
			setType(add, m);
		}
	}

	protected void setType(Location add, Material m) {
		if (add.getBlock().getType() != m) {
			add.getBlock().setType(m);
		}
	}

}
