package generate.hight_map.imple;

import generate.hight_map.HeightMapInterface;
import generate.hight_map.HeightMapUtil;
import generate.hight_map.mapInitializer.AroundSmoothMapInitializer;
import generate.hight_map.mapInitializer.MatrixInitializerInterface;

import org.bukkit.Location;

public class MountainHeightMap extends NormalHeightMap{
	int originalSize;

	
//	@Override
//	public HeightMapInterface setXLenghtZLenght(int xLenght, int zLenght) {
//		this.xLenght = xLenght;
//		this.zLenght = zLenght;
//		return this;
//	}
	
	@Override
	public HeightMapInterface generate(int size) {

		isFirst = true;

		originalSize = size;
		
		//指定範囲に収まるようなheightMapを作成する
		int subMapSize = HeightMapUtil.geTruncation2Multiplier(Math.min(xLength, zLength)) / 2;
		MountainHeightMapSub mountainHeightMapSub = (MountainHeightMapSub) new MountainHeightMapSub(subMapSize).setMin(min).setMax(max);
		if (initializerInterface instanceof AroundSmoothMapInitializer) {
			mountainHeightMapSub.setAroundSmoothFlg(true);
		}
		mountainHeightMapSub.generate(subMapSize);
		
		//map initializerを設定する
		MountainInitializer newInitializer = new MountainInitializer(mountainHeightMapSub.getHeightMap(), xLength, zLength);
		if (initializerInterface instanceof AroundSmoothMapInitializer) {
			AroundSmoothMapInitializer beforeInitializer = (AroundSmoothMapInitializer) initializerInterface;
			newInitializer.setAroundSmooth(beforeInitializer.minLoc, beforeInitializer.maxLoc);
		}
		initializerInterface = newInitializer;

		super.generate(size);

//		int marge = (int) Math.floor((getCreateSize() - originalSize)/2);
//
//		short[][] heightMap = getHeightMap();
//
//		setSize(originalSize);
//		for (int i = marge; i < originalSize + marge; i++) {
//			for (int j = marge; j < originalSize + marge; j++) {
//				setHeight(i - marge, j - marge, heightMap[i][j]);
//			}
//		}
		return this;
	}

	@Override
	protected void setHeight(int x, int y, short val) {
		//4辺を最小値にする　削除予定
//		if (x == 0|| y == 0 || x == heightMap.length - 1 || y == heightMap.length - 1) {
//			val = min;
//		}
		super.setHeight(x, y, val);
	}

	protected boolean isFirst = true;

	@Override
	protected short getError(int length) {
		short rtn = super.getError(length);
//		//最初だけ高くする
//		if (isFirst) {
//			rtn = (short) (max - 3);
//			isFirst = false;
//			return (short) Math.abs(rtn);
//		}
		return rtn;
	}
	
}

/**
 * 山を作るHeightMap
 * @author kensuke
 *
 */
class MountainHeightMapSub extends NormalHeightMap {
	protected boolean isFirst = true;
	
	
	int size;
	public MountainHeightMapSub(int size) {
		this.size = size;
	}

	@Override
	protected short getError(int length) {
		short rtn = super.getError(length);
		//最初だけ高くする
		if (isFirst) {
			rtn = (short) (max - 3);
			isFirst = false;
			return (short) Math.abs(rtn);
		}
		return rtn;
	}

	/**
	 * @param size 2^xになっているのが前提
	 */
	@Override
	public HeightMapInterface generate(int size) {
		int temp = 2;
		while (temp != size) {
			temp*=2;
			if (temp > size) {
				RuntimeException e = new RuntimeException("param is invalid!!");
				e.printStackTrace();
			}
		}
		return super.generate(size);
	}
	
	@Override
	public HeightMapInterface setMinLocMaxLoc(Location minLoc, Location maxLoc) {
		super.setMinLocMaxLoc(minLoc, maxLoc);
		int xMarge = (xLength - size) / 2;
		int zMarge = (zLength - size) / 2;
		super.setMinLocMaxLoc(minLoc.clone().add(xMarge, 0, zMarge), maxLoc.subtract(xMarge, 0, zMarge));
		return this;
	}
	
}

/**
 * subHeightMapをプレイヤーによって指定された範囲の中心に生成する
 *
 */
class MountainInitializer implements MatrixInitializerInterface  {
	public MountainInitializer(short[][] subHeightMap, int xLength, int zLength) {
		this.subHeightMap = subHeightMap;
		this.xLength = xLength;
		this.zLength =zLength;
	}
	
	int xLength;
	int zLength;
	
	AroundSmoothMapInitializer aroundSmoothMapInitializer = null;
	
	/**
	 * around smooth化する場合はセットする
	 * @param minLoc
	 * @param maxLoc
	 * @param xLength
	 * @param zLength
	 */
	public void setAroundSmooth(Location minLoc, Location maxLoc) {
		aroundSmoothMapInitializer = new AroundSmoothMapInitializer(minLoc, maxLoc, xLength, zLength);
	}
	
	short[][] subHeightMap;
	
	@Override
	public short[][] getMatrix(int size) {
		short[][] map;
		if (aroundSmoothMapInitializer == null) {
			map = new short[size][size];
		} else {
			map = aroundSmoothMapInitializer.getMatrix(size);
		}
		
		int xMarge = (xLength - subHeightMap.length) / 2;
		int zMarge = (zLength - subHeightMap.length) / 2;
		
		int xIndex = 0;
		for (int x = xMarge; x < xMarge + subHeightMap.length; x++) {
			int zIndex = 0;
			for (int z = zMarge; z < zMarge + subHeightMap.length; z++) {
				map[x][z] = subHeightMap[xIndex][zIndex];
				zIndex++;
			}
			xIndex++;
		}
		return map;
	}
	
}
