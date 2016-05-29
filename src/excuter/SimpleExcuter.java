package excuter;

import hight_map.HeightMapInterface;

import org.bukkit.Location;

import blockSetter.BlockSetterInterface;

public class SimpleExcuter implements GenerateExcuterInteface{

	protected Location corner;
	protected int xLenght;
	protected int zLenght;
	
	public SimpleExcuter(Location corner, int xLenght, int zLenght) {
		this.corner = corner;
		this.xLenght = xLenght;
		this.zLenght = zLenght;
	}
//	
//	/**
//	 * 生成を実行する
//	 * @param heightMap
//	 */
//	public void excute(HeightMapInterface heightMap) {
//	}
	
	@Override
	public void excute(BlockSetterInterface setter, HeightMapInterface hightMap) {
		//実行する
		hightMap.generate(Math.max(xLenght, zLenght));
		
		for (int x = 0; x < xLenght; x++) {
			for (int y = 0; y < zLenght; y++) {
				int height = hightMap.getHeight(x, y);
				setter.setBlock(corner.clone().add(x, 0, y), height);
			}
		}
	}

}
