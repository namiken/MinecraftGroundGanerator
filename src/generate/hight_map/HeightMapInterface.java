package generate.hight_map;

import org.bukkit.Location;

public interface HeightMapInterface {
	public HeightMapInterface generate(int size);

	public int getHeight(int x , int z);

	public short[][] getHeightMap();

	public int getCreateSize();

	public HeightMapInterface setSeed(long seed);

	public HeightMapInterface setMax(short max);

	public HeightMapInterface setMin(short min);
	
	public HeightMapInterface setAroundSmoothFlg(boolean isAroundSmooth);
	
	public HeightMapInterface setMinLocMaxLoc(Location minLoc, Location maxLoc);
}
