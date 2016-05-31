package generate.blockSetter;

import org.bukkit.Location;
import org.bukkit.Material;

public class StoneMountainAndLaveBlockSetter extends LiquidBlockSetter{
	@Override
	protected Material getLiquid(Location add, int height) {
		return Material.AIR;
	}

	@Override
	protected Material getLiquidButtom(Location add, int height) {
		return Material.STONE;
	}

	@Override
	protected Material getCoastline(Location add, int height) {
		return Material.STONE;
	}

//	ArrayList<Location> dirtList = new ArrayList<Location>();

	@Override
	protected Material getSurface(Location add, int height) {
//		if (height < 70 && 60 < height) {
//			//ランダムで土を置く
//			if (r.nextInt(5) == 0) {
//				dirtList.add(add);
//				return Material.DIRT;
//			//土が隣接していれば更に土をランダムでおく
//			} else if (nextDirt(add)) {
//				if (r.nextInt(3) == 0) {
//					dirtList.add(add);
//					return Material.DIRT;
//				}
//			}
//		}
//
//		if (r.nextInt(15) == 0) {
//			return Material.LAVA;
//		}

		return Material.STONE;
	}

//	public boolean nextDirt(Location add) {
//		for (Location loc : dirtList) {
//			if (loc.getY() == add.getY()) {
//				if (loc.distance(add) <= 1) {
//					return true;
//				}
//			} else if (Math.abs(loc.getY() - add.getY()) == 1) {
//				if (loc.distance(add) < 2) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	@Override
	protected Material getSurfaceUnder(Location add, int height) {
		return Material.STONE;
	}

}
