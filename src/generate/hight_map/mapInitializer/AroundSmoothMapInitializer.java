package generate.hight_map.mapInitializer;

import org.bukkit.Location;

public class AroundSmoothMapInitializer implements MatrixInitializerInterface{
	Location minLoc;
	Location maxLoc;
	
	int xLength;
	int zLength;
	
	public AroundSmoothMapInitializer(Location minLoc, Location maxLoc, int xLength, int zLength) {
		this.minLoc = minLoc;
		this.maxLoc = maxLoc;
		this.xLength = xLength;
		this.zLength = zLength;
	}

	@Override
	public short[][] getMatrix(int size) {
		short[][] rtn = new short[size][size];
		
		//一片
		for (int x = 0; x < xLength; x++) {
			rtn[x][0] = getMaxHeight(minLoc, x, -1);
		}
		//一片
		for (int z = 0; z < zLength; z++) {
			rtn[0][z] = getMaxHeight(minLoc, -1, z);
		}
		
		for (int x = xLength; x < size; x++) {
			for (int z = 0; z < zLength; z++) {
				rtn[x][z] = getMaxHeight(minLoc, xLength + 1, z);
			}
		}

		for (int z = zLength; z < size; z++) {
			for (int x = 0; x < zLength; x++) {
				rtn[x][z] = getMaxHeight(minLoc, x, zLength + 1);
			}
		}
		return rtn;
	}

	private short getMaxHeight(Location add, int x, int z) {
//		Block highestBlockAt = add.getWorld().getHighestBlockAt(add.clone().add(x, 0 ,z));
//		return (short) highestBlockAt.getY();
		return 2;
	}

}
