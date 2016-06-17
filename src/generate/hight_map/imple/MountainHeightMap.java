package generate.hight_map.imple;

import java.util.Random;

import generate.hight_map.HeightMapInterface;
import generate.hight_map.HeightMapUtil;
import generate.hight_map.mapInitializer.AroundSmoothMapInitializer;
import generate.hight_map.mapInitializer.MatrixInitializerInterface;

import org.bukkit.Location;

public class MountainHeightMap extends NormalHeightMap{
	int originalSize;

	@Override
	public HeightMapInterface generate(int size) {

		isFirst = true;

		originalSize = size;

		//指定範囲に収まるようなheightMapを作成する
		int subMapSize = HeightMapUtil.geTruncation2Multiplier(Math.min(xLength, zLength)) / 2;
		MountainHeightMapSub mountainHeightMapSub = (MountainHeightMapSub) new MountainHeightMapSub(subMapSize).setMin(min).setMax(max);
		if (seedSetFlg) {
			mountainHeightMapSub.setSeed(seed);
		}
		//隣接するブロックを特定出来ないためとりあえず未対応
//		if (initializerInterface instanceof AroundSmoothMapInitializer) {
//			mountainHeightMapSub.setAroundSmoothFlg(true);
//		}
		mountainHeightMapSub.generate(subMapSize);


		//map initializerを設定する
		MountainInitializer newInitializer = new MountainInitializer(mountainHeightMapSub.getHeightMap(), xLength, zLength);
		if (seedSetFlg) {
			newInitializer.setSeed(seed);
		}
		if (initializerInterface instanceof AroundSmoothMapInitializer) {
			AroundSmoothMapInitializer beforeInitializer = (AroundSmoothMapInitializer) initializerInterface;
			newInitializer.setAroundSmooth(beforeInitializer.minLoc, beforeInitializer.maxLoc);
		}
		initializerInterface = newInitializer;

		//生成
		super.generate(size);

		//mountainHeightMapSubと組み合わせる
		addMapOnCenter(this.heightMap, mountainHeightMapSub.getHeightMap());

		return this;
	}

	boolean seedSetFlg = false;
	long seed = 0;

	@Override
	public HeightMapInterface setSeed(long seed) {
		seedSetFlg = true;
		this.seed = seed;
		return super.setSeed(seed);
	}

	protected short[][] addMapOnCenter(short[][] bigMap, short[][] smallMap) {
		short[][] map = bigMap;
		int xMarge = (xLength - smallMap.length) / 2;
		int zMarge = (zLength - smallMap[0].length) / 2;

		int xIndex = 0;
		for (int x = xMarge; x < xMarge + smallMap.length; x++) {
			int zIndex = 0;
			for (int z = zMarge; z < zMarge + smallMap[0].length; z++) {
				map[x][z] = smallMap[xIndex][zIndex];
				zIndex++;
			}
			xIndex++;
		}
		return map;
	}

	@Override
	protected void setHeight(int x, int y, short val) {
		super.setHeight(x, y, val);
	}

	protected boolean isFirst = true;

	@Override
	protected short getError(int length) {
		short rtn = super.getError(length / 2);
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

	Random r = new Random();

	public void setSeed(long seed) {
		r = new Random(seed);
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
				map[x][z] = getInitilizeHeight(xIndex, zIndex, size);
				zIndex++;
			}
			xIndex++;
		}
		return map;
	}

	public short getInitilizeHeight(int xIndex, int zIndex, int size) {
		//４辺から求めるのは今のところなし
//		short val;
//
//		//角度を計算
//		double atan2 = Math.atan2(xIndex - (size/2), zIndex - (size/2)) + Math.PI;
//
//		if ((atan2 <= Math.PI * 0.25  && atan2 >= 0)|| (atan2 > Math.PI * 1.75 && atan2 <= Math.PI * 2)) {
//		//右
//			val = (short) (subHeightMap[xIndex][0]);
//		} else if (atan2 > Math.PI * 0.25 && atan2 <= Math.PI * 0.75) {
//		//上
//			val = (short) (subHeightMap[0][zIndex]);
//		} else if (atan2 > Math.PI * 0.75 && atan2 <= Math.PI * 1.25) {
//		//左
//			val = (short) (subHeightMap[xIndex][subHeightMap[0].length - 1]);
//		} else if (atan2 > Math.PI * 1.25 && atan2 <= Math.PI * 1.75) {
//		//下
//			val = (short) (subHeightMap[subHeightMap.length - 1][zIndex]);
//		} else {
//			new RuntimeException(atan2 + "is invalid").printStackTrace();
//			val = 60;
//		}
//		val = 60;
		return subHeightMap[xIndex][zIndex];
	}

}
