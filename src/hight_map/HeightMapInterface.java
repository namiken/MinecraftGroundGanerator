package hight_map;

public interface HeightMapInterface {
	public HeightMapInterface generate(int size);
	
	public int getHeight(int x , int z);
	
	public short[][] getHeightMap();
	
	public int getCreateSize();
	
	public HeightMapInterface setSeed(long seed);
	
	public HeightMapInterface setMaxMin(short max, short min);
}
