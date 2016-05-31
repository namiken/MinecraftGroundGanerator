package generate.blockSetter;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;

public abstract class LiquidBlockSetter implements BlockSetterInterface{

	protected Random r = new Random();
	@Override
	public void setSeed(long seed) {
		r = new Random(seed);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void setBlock(Location add, int height) {
		int maxHeight = add.getWorld().getMaxHeight();
		for (int y = 0; y < maxHeight; y++) {
			add.setY(y);
			add.getBlock().setTypeId(0);
			Material m = Material.AIR;
			if (y < 2) {
				m = Material.BEDROCK;
			}
//			}else if (height < 60) {
//				if (height < y && y <= 60) {
//					m = getLiquid(add, height);
//				} else if (height - 3 < y) {
//					if (y < 56) {
//						m = getLiquidButtom(add, height);
//					} else {
//						m = getCoastline(add, height);
//					}
//				} else if (y <= height) {
//					m = getUnderGround(add, height);
//				}
//			} else if (height < 63){
//				if (y < height - 3) {
//					m = getUnderGround(add, height);
//				} else if (y <= height) {
//					m = getCoastline(add, height);
//				}
//			} else {
//				if (y < height - 3) {
//					m = getUnderGround(add, height);
//				} else if (y < height) {
//					m = getSurfaceUnder(add, height);
//				} else if (y == height) {
//					m = getSurface(add, height);
//				}
//			}
				else if (height < y) {
					if (y < 60) {
						m = getLiquid(add, height);
					} else {
						//AIR
					}
				} else if (height - 3 <= y && y <= height) {
					if (56 < y && y < 63) {
						m = getCoastline(add, height);
					} else if (63 <= y) {
						if (y == height) {
							m = getSurface(add, height);
						} else {
							m = getSurfaceUnder(add, height);
						}
					} else {
						if (y == height) {
							m = getLiquidButtom(add, height);
						} else {
							m = getSurfaceUnder(add, height);
						}
					}
				} else if (y < height - 3) {
					m = getSurfaceUnder(add, height);
				}
			setBlockType(add, m);
		}
	}

	protected void setBlockType(Location add, Material m) {
		if (add.getBlock().getType() != m) {
			add.getBlock().setType(m);
		}
	}

	abstract protected Material getLiquid(Location add, int height);

	abstract protected Material getLiquidButtom(Location add, int height);

	abstract protected Material getCoastline(Location add, int height);

	abstract protected Material getSurface(Location add, int height);

	abstract protected Material getSurfaceUnder(Location add, int height);

	protected Material getUnderGround(Location add, int height) {
		return Material.STONE;
	}
}
